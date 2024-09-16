class Foo {
private:
    int currentTurn = 1;
    mutex m;
    condition_variable cv;

    void run_on_turn(function<void()> runnable, int turn) {
        {
            unique_lock<mutex> lock(m);
            cv.wait(lock, [this, turn]{ return currentTurn == turn; });
        }
        runnable();
        {
            unique_lock<mutex> lock(m);
            currentTurn++;
        }
        cv.notify_all();
    }

public:
    Foo() {
        
    }

    void first(function<void()> printFirst) {
        run_on_turn(printFirst, 1);
    }

    void second(function<void()> printSecond) {
        run_on_turn(printSecond, 2);
    }

    void third(function<void()> printThird) {
        run_on_turn(printThird, 3);
    }
};