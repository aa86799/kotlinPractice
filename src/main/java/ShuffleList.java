import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ShuffleList {
    private static List<String> mActionList;
    private static Random random = new Random();
    static List<Integer> randomIndexList;

    public static void main(String[]args){
        mActionList = new ArrayList<String>();
        mActionList.add("bling");
        mActionList.add("nod");
        mActionList.add("ShakeHead");

        shuffle();
        System.out.println(mActionList);
    }

    static void shuffle() {
        if (!mActionList.isEmpty()) {
            randomIndexList = new ArrayList<>(mActionList.size());
            //            Collections.shuffle(mActionList);
            int size = mActionList.size();
            for (int i = 0; i < size; i++) {
                int index = randomIndex(size);
                randomIndexList.add(index);
            }
            List<String> result = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                result.add(mActionList.get(randomIndexList.get(i)));
            }
            System.out.println(randomIndexList);
            mActionList = result;
        }
    }

    static int randomIndex(int size) {
        int index = random.nextInt(size);
        while (randomIndexList.contains(index)) {
            index = random.nextInt(size);
            System.out.println("do while once");
        }
        return index;
    }

}