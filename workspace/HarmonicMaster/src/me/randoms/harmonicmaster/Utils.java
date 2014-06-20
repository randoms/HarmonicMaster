package me.randoms.harmonicmaster;

public final class Utils {
	
	public static int[] getTopSix(short[] input){
		int[] addedList = {-1,-1,-1,-1,-1,-1};
		
		for(int i=0;i<6;i++){
			short max = Short.MIN_VALUE;
			int max_index = -1;
			for(int j=0;j<input.length;j++){
				if(input[j]>max){
					// 没有已经添加
					boolean addedFlag = false;
					for(int k=0;k<6;k++){
						if(j == addedList[k])addedFlag = true;
					}
					if(!addedFlag){
						max_index = j;
						max = input[j];
					}
				}
				// 添加到addedList
				addedList[i] = max_index;
			}
		}
		return addedList;
	}
	
	public static int[] getTopSix(double[] input){
		int[] addedList = {-1,-1,-1,-1,-1,-1};
		
		for(int i=0;i<6;i++){
			double max = Double.MIN_VALUE;
			int max_index = -1;
			for(int j=0;j<input.length;j++){
				if(input[j]>max){
					// 没有已经添加
					boolean addedFlag = false;
					for(int k=0;k<6;k++){
						if(j == addedList[k])addedFlag = true;
					}
					if(!addedFlag){
						max_index = j;
						max = input[j];
					}
				}
				// 添加到addedList
				addedList[i] = max_index;
			}
		}
		return addedList;
	}
	
	public static int[] findPeaks(double[] input){
		// find the top 6 peaks
		int[] addedList = {-1,-1,-1,-1,-1,-1};
		
		for(int i=0;i<input.length;i++){
			// find peaks
			if(i !=0 && i != input.length-1){
				if(input[i-1]<input[i] && input[i]>input[i+1]){
					//check if this peaks if big enough
					boolean bigFlag = false;
					for(int j= addedList.length-1;j>=0;j--){
						if(addedList[j] == -1 || input[i]>input[addedList[j]]){
							bigFlag = true;
							// 继续和更大的记录比较
						}else{
							if(bigFlag){
								// 没有比这个大，但是比下一个大
								insert(addedList,j+1,i);
								bigFlag = false;
							}
						}
					}
					if(bigFlag){
						addedList = insert(addedList,0,i); //比所有的都大
					}
				}
			}
		}
		
		return addedList;
	}
	
	public static int[] insert(int[] input,int index,int value){
		if(index == input.length-1){
			input[input.length-1] = value;
			return input;
		}
		
		for(int i=input.length-1;i>index;i--){
			// 把index之后的数据往后移一位，丢掉最后一个
			input[i] = input[i -1];
		}
		input[index] = value;
		return input;
	}
	
	public static String arrayToString(int[] input){
		String res = "[";
		for(int i=0;i<input.length;i++){
			res = res + String.valueOf(input[i])+", ";
		}
		res = res +"]\n";
		return res;
	}
}
