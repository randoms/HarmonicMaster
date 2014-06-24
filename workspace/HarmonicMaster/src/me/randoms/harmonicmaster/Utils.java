package me.randoms.harmonicmaster;


public final class Utils {
	
	public static int[] getTopSix(short[] input){
		int[] addedList = {-1,-1,-1,-1,-1,-1};
		
		for(int i=0;i<6;i++){
			short max = Short.MIN_VALUE;
			int max_index = -1;
			for(int j=0;j<input.length;j++){
				if(input[j]>max){
					// 娌℃湁宸茬粡娣诲姞
					boolean addedFlag = false;
					for(int k=0;k<6;k++){
						if(j == addedList[k])addedFlag = true;
					}
					if(!addedFlag){
						max_index = j;
						max = input[j];
					}
				}
				// 娣诲姞鍒癮ddedList
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
					// 娌℃湁宸茬粡娣诲姞
					boolean addedFlag = false;
					for(int k=0;k<6;k++){
						if(j == addedList[k])addedFlag = true;
					}
					if(!addedFlag){
						max_index = j;
						max = input[j];
					}
				}
				// 娣诲姞鍒癮ddedList
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
							// 缁х画鍜屾洿澶х殑璁板綍姣旇緝
						}else{
							if(bigFlag){
								// 娌℃湁姣旇繖涓ぇ锛屼絾鏄瘮涓嬩竴涓ぇ
								insert(addedList,j+1,i);
								bigFlag = false;
							}
						}
					}
					if(bigFlag){
						addedList = insert(addedList,0,i); //姣旀墍鏈夌殑閮藉ぇ
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
			// 鎶奿ndex涔嬪悗鐨勬暟鎹線鍚庣Щ涓�綅锛屼涪鎺夋渶鍚庝竴涓�
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
	
	/**
	 * @param input
	 * @return
	 * to String with a fixed length
	 */
	public static String fixToString(String input,int length){
		char[] res = new char[length];
		for(int i=0;i<length;i++){
			if(i < input.length()){
				res[i] = input.charAt(i);
			}else{
				res[i] = ' ';
			}
		}
		return String.valueOf(res);
	}
}
