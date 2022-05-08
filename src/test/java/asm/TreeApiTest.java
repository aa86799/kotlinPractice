package asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.FileInputStream;
import java.util.List;

/**
 * tree 方式遍历. ClassNode、MethodNode、FieldNode
 */
public class TreeApiTest implements Opcodes {

    public static void main(String[] args) throws Exception {
//        Class<?> clazz = User.class;
        User user = new User("stone", 33);
        Class<User> clazz = (Class<User>) user.getClass();
        String clazzFilePath = Utils.getClassFilePath(clazz);
        ClassReader classReader = new ClassReader(new FileInputStream(clazzFilePath));
        ClassNode classNode = new ClassNode(ASM9); // public class ClassNode extends ClassVisitor
        classReader.accept(classNode, 0); // accept()方法完成对 class 遍历，并把相关信息记录到 ClassNode 对象中

        List<MethodNode> methods = classNode.methods;
        List<FieldNode> fields = classNode.fields;

        System.out.println("methods:");
        for (MethodNode methodNode : methods) {
            System.out.println(methodNode.name + ", " + methodNode.desc);
        }

        System.out.println("fields:");
        for (FieldNode fieldNode : fields) {
            System.out.println(fieldNode.name + ", " + fieldNode.desc);
        }

        /* 输出 User_Copy.class */
        ClassWriter classWriter = new ClassWriter(0);
        classReader.accept(classWriter, 0);
        // 写入文件
        Utils.writeFile(classWriter,"/Users/stone/Documents/project_ij/kotlinPractice/build/classes/java/test/asm/User_Copy.class");

        /* 添加field，writer 。 FieldNode、MethodNode 都有构造方法，可以传ASM版本号，或不传 */
        ClassWriter classWriter1 = new ClassWriter(0);
        classNode.fields.add(new FieldNode(Opcodes.ASM9, Opcodes.ACC_PRIVATE|Opcodes.ACC_STATIC|Opcodes.ACC_FINAL,
                "weight", "D", "10241024", 128.5)); // static final 时，初始化值才有效
        // 仅是定义方法的声明
        MethodNode getWeightMethod = new MethodNode(Opcodes.ACC_PUBLIC, "getWeight", "(I)D", "10241024", null) {
            @Override
            public void visitCode() {
                super.visitCode();
            }
        };
        classNode.methods.add(getWeightMethod);
        classNode.accept(classWriter1);
        Utils.writeFile(classWriter1,"/Users/stone/Documents/project_ij/kotlinPractice/build/classes/java/test/asm/User_Copy1.class");
    }

}
