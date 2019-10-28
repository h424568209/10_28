import java.util.*;

public class LeeCode {
    /**
     *所求排列一定在叶子结点处得到；
     * 如果k大于这个分支所产生的叶子结点数，直接跳过这个分支
     * 如果k小于等于这个分支产生的叶子结点数，说明所求的全排列一定在这一个分支将要产生的叶子结点中
     * 计算阶乘来计算叶子结点的第几个数
     * @param n   元素个数
     * @param k   元素所在位置
     * @return   数字所组合成 的数字
     */
    public String getPermutation(int n, int k) {
        int []nums = new int[n];
        boolean[]used = new boolean[n];
        for(int i = 0;i< n;i++){
            nums[i] = i+1;
        }
        List<String> pre = new ArrayList<>();
        return dfs(nums,used,n,k,0,pre);
    }

    private String dfs(int[] nums, boolean[] used, int n, int k, int depth, List<String> pre) {
        if(depth==n){
            StringBuilder sb = new StringBuilder();
            for(String c:pre){
                sb.append(c);
            }
            return sb.toString();
        }
        //后面的数的全排列个数
        int ps = factorial(n-1-depth);
        for(int i = 0;i<n ;i++){
            if(used[i]){
                continue;
            }
            //后面的数的全排列个数小于k的时候 进行剪枝操作
            if(ps<k){
                k-=ps;
                continue;
            }
            pre.add(nums[i]+"");
            //走到叶子节点 不用恢复
            used[i] = true;
            return dfs(nums,used,n,k,depth+1,pre);
        }
        return null;
    }

    private int factorial(int n) {
        int res = 1;
        while(n>0){
            res*=n;
            n--;
        }
        return res;
    }

    /**
     * 回溯搜索 =  深度优先遍历+  状态重置 + 剪枝
     * @param nums int类型数组
     * @return 数组中元素所组成的所有不重复的可能
     */
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> list = new ArrayList<>();
        boolean[]used = new boolean[len];
        if(len ==0){
            return list;
        }
        Recall(nums,used,0,len,new Stack<>(),list);
        return list;
    }

    private void Recall(int[] nums, boolean[] vistied, int curSize, int len, Stack<Integer> path, List<List<Integer>> list) {
        if(curSize == len){
            list.add(new ArrayList<>(path));
            return;
        }
        for(int i = 0;i<len;i++){
            if(!vistied[i]){
                //深度遍历
                path.add(nums[i]);
                vistied[i] = true;
                //回溯
                Recall(nums,vistied,curSize+1,len,path,list);
                //状态重置
                path.pop();
                vistied[i] = false;
            }
        }
    }

    /**
     * 集合中不包含重复的组合 所以对数组进行排序
     * 数组中的元素不可以重复使用 所以按照顺序减去数组中得元素递归求解，遇到0进行结算且回溯，遇到负数则进行回溯
     * 以target为根节点 依次减去数组中的元素，值到小于或者等于0
     * @param candidates 无序的含重复元素的数组
     * @param target 目标值
     * @return 数组中不可重复使用的元素的和等于目标值的集合
     */
    private List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if(candidates.length == 0){
            return list;
        }
        Arrays.sort(candidates);
        int len = candidates.length;
        reCall(candidates,target,len,0,new Stack<>());
        return list;
    }

    private void reCall(int[] candidates, int target, int len, int start, Stack<Integer> stack) {
        if(target == 0){
            list.add(new ArrayList<>(stack));
            return;
        }
        for(int i = start; i<len && target - candidates[i]>=0;i++){
            //进行重复元素的消除（在已经排好序为前提）
            if(i>start&&candidates[i]==candidates[i-1]){
                continue;
            }
            stack.add(candidates[i]);
            //因为元素不可以重复使用 所以传递下去的是i+1
            reCall(candidates,target-candidates[i],len,i+1,stack);
            stack.pop();
        }
    }

    private List<List<Integer>> list = new ArrayList<>();
    /**
     * 使用回溯算法 将给定数组排序是为了消除重复，后面只会用数组中比当前元素大的值进行计算来保证不重复
     * 而后进行减法的回溯 直到减到当前数小于等于0 ：若等于零 则是我们要找的元素，否则进行回溯
     * 只用栈来存放加进去的元素 这样回溯的话直接进行出栈就可以了
     * @param candidates 无序的无重复元素的数组
     * @param target 目标值
     * @return 数组中元素（可以重复使用）的和等于目标值
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if(candidates.length==0){
            return list;
        }
        Arrays.sort(candidates);
        int end = candidates.length;
        backstrack(candidates,0,end,target,new Stack<>());
        return list;
    }


    private void backstrack( int[] candidates, int start, int end, int target, Stack<Integer> stack) {
        if(target == 0){
            list.add(new ArrayList<>(stack));
            return;
        }
        for(int i = start; i< end && target-candidates[i] >= 0; i++){
            stack.add(candidates[i]);
            backstrack(candidates,i,end,target-candidates[i],stack);
            stack.pop();
        }
    }

    public static void main(String[] args) {
               LeeCode l = new LeeCode();
       int[]arr = {10,1,2,7,6,1,5};
//        System.out.println(l.combinationSum2(arr,8));
               System.out.println(l.permute(arr));
        System.out.println(l.getPermutation(3,3));
    }
}
