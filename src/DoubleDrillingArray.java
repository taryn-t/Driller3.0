


 class DoubleDrillingArray<E> {
	E[] doubledArray;
	int doubleCapacity;
	
	DoubleDrillingArray(){
		doubledArray = (E[]) new Object[10];
		doubleCapacity = 0;
	}
	DoubleDrillingArray(Object[] arrayToDouble){
		doubleCapacity = arrayToDouble.length*2;
	
		if(doubleCapacity > 0 && doubleCapacity < Integer.MAX_VALUE) {
			doubledArray = (E[]) new Object[doubleCapacity];
			for(int i = 0; i<arrayToDouble.length; i++) {
				doubledArray[i] = (E) arrayToDouble[i];
			}
		}
	}
	 public E[] getDblArray() {
		 return doubledArray;
	 }
	 public int getDoubleCapacity() {
		return doubleCapacity;
	}
	public E[] getDoubledArray(){
		return doubledArray;
	}
	public void setDoubleCapacity(int capacity) {
		doubleCapacity = capacity;
	}
}
