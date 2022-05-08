package asm;


import org.objectweb.asm.*;

import java.io.FileInputStream;

/**
 * 监听“节点”的解析事件.
 * ClassVisitor, visitField() 、visitMethod()
 *
 * 关于要插入的代码，可以先写出源码文件，然后 javac ->.class ，再javap 看信息。对照着写
 */
public class VisitApiTest {

    private static String mOwner;

    public static void main(String[] args) throws Exception {
        Class<?> clazz = User.class;
        String clazzFilePath = Utils.getClassFilePath(clazz);
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));
        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                System.out.println("visit field:" + name + " , desc = " + descriptor);
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("visit method:" + name + " , desc = " + descriptor);
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };
        classReader.accept(classVisitor, 0);

        ClassWriter classWriter = new ClassWriter(0);
        AddTimerClassVisitor acv = new AddTimerClassVisitor(Opcodes.ASM9, classWriter);
        classReader.accept(acv, 0);
        Utils.writeFile(classWriter, "/Users/stone/Documents/project_ij/kotlinPractice/build/classes/java/test/asm/User_Copy2.class");


//        ClassWriter classWriter3 = new ClassWriter(0);
        // COMPUTE_MAXS，自动计算最大堆栈大小和方法局部变量的最大数目的标志。MethodVisitor的visitMaxs方法将被忽略
        ClassWriter classWriter3 = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        AddTimerClassVisitor acv2 = new AddTimerClassVisitor(Opcodes.ASM9, new ClassVisitor(Opcodes.ASM5, classWriter3) {

            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
                mOwner = name;
                System.out.println("mOwner----" + mOwner);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                System.out.println("name----" + name + ", descriptor----" + descriptor);
                MethodVisitor origin = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (origin != null && !name.equals("<init>")) { // 匹配非构造方法
                    return new AddTimerMethodVisitor(Opcodes.ASM9, origin);
                }
                return origin;
            }
        });
        classReader.accept(acv2, 0);
        Utils.writeFile(classWriter3, "/Users/stone/Documents/project_ij/kotlinPractice/build/classes/java/test/asm/User_Copy3.class");

    }

    // 自定义 class visitor;
    /*
     * 执行顺序
     * visit visitSource? visitOuterClass? ( visitAnnotation | visitAttribute )*
        ( visitInnerClass | visitField | visitMethod )*
        visitEnd
        ?代表这个方法可能不会调用，*标识可能会调用 0 次或者多次
     */
    static class AddTimerClassVisitor extends ClassVisitor {

//        public AddTimerClassVisitor(int api) {
//            super(api);
//        }

        public AddTimerClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public void visitEnd() {
            // super: protected ClassVisitor cv
            FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC|Opcodes.ACC_STATIC, "timer",
                    "J", null, 234234L); // static final 时，初始化值才有效
            if (fv != null) {
                fv.visitEnd();
            }
            cv.visitEnd();
        }
    }

    /*
     *  MethodVisitor 执行顺序
     *  visitAnnotationDefault?
     *   (visitAnnotation |visitParameterAnnotation |visitAttribute )*
     *   ( visitCode
     *       (visitTryCatchBlock |visitLabel |visitFrame |visitXxxInsn | visitLocalVariable |visitLineNumber )*
     *       visitMaxs
     *   )? visitEnd
     */
    static class AddTimerMethodVisitor extends MethodVisitor {

//        protected AddTimerMethodClassVisitor(int api) {
//            super(api);
//        }

        protected AddTimerMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitCode() {
            /*
            拿当前类静态变量 timer，压到操作数栈
            调用 System. System.currentTimeMillis，方法返回值压到操作数栈；
            调用 “timer - System. System.currentTimeMillis”，结果压栈
            将 3 得到的值，再次赋值给 timer 字段；

            即 timer -= System.currentTimeMillis();
             */
            // 访问属性 "timer", get .   Insn = instruction 指令
            mv.visitFieldInsn(Opcodes.GETSTATIC, mOwner, "timer", "J");
            // 访问一个方法，静态调用
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
                    "currentTimeMillis", "()J", false);
            // LSUB, 查看 jvm的操作码值说明：https://docs.oracle.com/javase/specs/jvms/se9/html/jvms-6.html
            mv.visitInsn(Opcodes.LSUB); // 减去
            // 访问属性 "timer", put
            mv.visitFieldInsn(Opcodes.PUTSTATIC, mOwner, "timer", "J");

            // super: protected MethodVisitor mv;
            super.visitCode();
        }

        // RETURN 之前：我们选择复写 visitXxxInsn，再其内部判断当前指令是否是 RETURN；
        @Override
        public void visitInsn(int opcode) {
            // IRETURN => Return int from method   如果确定方法没有返回值，那么只要判断 RETURN 即可。
            // opcode 是return || ATHROW => Throw exception or error
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, mOwner, "timer", "J");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System",
                        "currentTimeMillis", "()J", false);
                mv.visitInsn(Opcodes.LADD);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, mOwner, "timer", "J");
            }
            mv.visitInsn(opcode);
            super.visitInsn(opcode);
        }

        /*
         * 通过 正常编写一个 java源文件，某个方法内部包含添加 timer 的代码； javac后 得到 class文件；
         * javap -v xxx.class 能看到 在目标方法中，如：
         * public java.lang.String getName();
                descriptor: ()Ljava/lang/String;
                flags: ACC_PUBLIC
                Code:
                  stack=4, locals=2, args_size=1
            在这个方法中所有的指令执行完成，所需要的栈的深度在编译期就确定了，从反编译结果来 stack = 4。
            所以重写本方法，设置maxStack.
            有可能之前某个方法 maxStack 就比 4大了，那直接设置4 就不行了。

            如下操作的栈空间计数(long 值占两个位置)：
            getstatic timer // 压栈 2
            invoke System.currentTimeMillis // 压栈 2
            LSUB // 出栈4，压栈 2。 前面的出栈后，相减，再将结果压栈
            put static timer // 出栈 2
         */
        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            mv.visitMaxs(maxStack + 4, maxLocals);
        }
    }
}
