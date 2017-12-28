package rxtest;


import org.junit.Test;
import rx.Observable;
import rx.observables.ConnectableObservable;

import java.nio.channels.NoConnectionPendingException;

public class ErrorHanlding {

    private class NoFives extends Exception {}

    public Observable<Integer> obs1thru1000() {
        return Observable.range(1, 1000);
    }

    public Observable<Integer> errorOnFive() {
        return obs1thru1000().map(i -> {
            if(i == 5) throw new NoConnectionPendingException();
            else return i;
        });
    }

    @Test
    public void testErrorOnGive() {
        errorOnFive().subscribe(i ->
                System.out.println("Number is" + i),
                t -> System.out.println("Received error " + t));
    }

    @Test
    public void testErrorReturn() {
        ConnectableObservable c = errorOnFive().publish();
        errorOnFive().onErrorReturn( t -> 0)
                .subscribe(i -> System.out.println("Number is: " + i ));
    }

    @Test
    public void testErrorRetry() {
        errorOnFive().retry().takeUntil(i -> i == 4)
                .subscribe(i -> System.out.println("Number is: " + i ));
    }


    @Test
    public void testErrorContinue() {
        errorOnFive().onErrorResumeNext(
                Observable.range(6, 995))
                .subscribe(i -> System.out.println("Number is: " + i ));
    }
}
