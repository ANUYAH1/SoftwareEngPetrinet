package logic;

public class CoverabilityTree {
    public class CoverabilityNode{
        public int getNumChildren(){
            return 0;
        }
        public CoverabilityNode getChild(int num){
            return null;
        }
        public CoverabilityNode loopedParent(){ //If there is an ancestor that is greater than or equal to this, then this will get the reference.
            return null;
        }
        

    }
    public CoverabilityNode getRoot(){
        return null;
    }
}
