class Foo {

    int nextIteration = 1;   

    public Foo() {
        
    }

    private void runOnTurn(Runnable runnable, int turn) throws InterruptedException {
        synchronized(this) {
            while (nextIteration < turn) {
                this.wait();
            }
            if (nextIteration != turn) {   throw new IllegalStateException();  }
        }
        runnable.run();
        synchronized(this) {
            nextIteration++;
            this.notifyAll();
        }
    }

    public void first(Runnable printFirst) throws InterruptedException {
        runOnTurn(printFirst, 1);
    }

    public void second(Runnable printSecond) throws InterruptedException {
        runOnTurn(printSecond, 2);
    }

    public void third(Runnable printThird) throws InterruptedException {
        runOnTurn(printThird, 3);
    }
}