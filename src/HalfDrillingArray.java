//class that returns a halved array as a object
    class HalfDrillingArray<E> {
        E[] halvedArray;
        int halfCapacity;

        //default constructor
        HalfDrillingArray(){
            halvedArray = (E[]) new Object[10];
            halfCapacity = 0;
        }
        //halves a given  array
        HalfDrillingArray(Object[] arrayToHalf){
            halfCapacity = arrayToHalf.length/2;

            if(halfCapacity > 0 && halfCapacity >Integer.MIN_VALUE) {
                halvedArray = (E[]) new Object[halfCapacity];
                for(int i = 0; i <halfCapacity; i++) {
                    if(i!=5){
                        halvedArray[i] = (E) arrayToHalf[i];
                    }

                }
            }
        }
        public E[] getHalfArray() {
            return halvedArray;
        }
        public int getHalfCapacity() {
            return halfCapacity;
        }

    }

