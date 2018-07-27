package com.yunpay.sdk.test;

/**
 * 快速排序
 * 快速排序采用了分治策略。就是在一个数组中取一个基准数字，把小的数放基准的左边，大的数放基准的右边。
 * 基准左边和右边分别是新的序列。在新的序列中再取一个基准数字，小的放左边，大的放右边。
 * 这个里面用到的递归。我们需要三个参数，一个是数组，另外两个是序列的边界
 * @author HJS
 */
public class QuickSort{

    void sort(int num[],int left,int right){
        if (left<right){
            int index=partition(num,left,right); //算出枢轴值 
            sort(num,left,index-1);       //对低子表递归排序
            sort(num,index+1,right);        //对高子表递归排序
        }
    }

    /**
     * 调用partition(num,left,right)时，对num[]做划分，
     * 并返回基准记录的位置
     * @param num
     * @param left
     * @param right
     * @return
     */
    public int partition(int[] num,int left,int right){
        if(num==null || num.length<=0 || left<0 || right>=num.length){
            return 0;
        }
        int prio=num[left+(right-left)/2];   //获取数组中间元素的下标
        while (left<=right){                 //从两端交替向中间扫描
            while (num[left]<prio)
                left++;
            while (num[right]>prio)
                right--;
            if (left<=right){
                swap(num,left,right);        //最终将基准数归位  
                left++;
                right--;
            }
        }
        return left;
    }


    public void swap(int[] num,int left,int right){
        int temp = num[left];
        num[left] = num[right];
        num[right] = temp;
    }
    public static void main(String args[]){
        int[] num={7,3,5,1,2,8,9,2,6};
        new QuickSort().sort(num,0,num.length-1);
        for(int n:num) {
            System.out.print(n+" ");
        }
    }
}
