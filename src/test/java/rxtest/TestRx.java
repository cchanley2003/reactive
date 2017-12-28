package rxtest;

import org.junit.Test;
import rx.Observable;
import rx.observables.JoinObservable;
import rx.schedulers.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TestRx {

    public Observable<Integer> obs1thru10() {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return Observable.from(nums);
    }

    public Observable<Integer> times2() {
        return obs1thru10().map(i -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Running times two.");
            return i * 2;
        });
    }

    public Observable<Integer> add100() {
        return times2().map(i -> i + 100);
    }

    public Stream<Integer> times2Stream() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream().map(i -> {
           System.out.println("Running streams times 2");
           return i * 2;
        });
    }

    @Test
    public void testAndThenWhen() {
        JoinObservable.when(JoinObservable.from(add100()).and(obs1thru10()).
        then(Math::max)).toObservable().subscribe(i -> System.out.println("Number " + i));

    }

    @Test
    public void runRxTest() throws InterruptedException {
        times2().subscribeOn(Schedulers.computation()).
                subscribe(i ->  System.out.println("Number is: " + i));
        System.out.println("In between");
        add100().subscribeOn(Schedulers.computation()).
                subscribe(i ->  System.out.println("Number is: " + i));
        //times2().subscribe( i -> System.out.println("Number is: " + i));
        //times2();
        Thread.sleep(1000);
    }

    @Test
    public void runStreamTest()
    {
        Observable.create(emitter -> {

        });
        times2Stream();
    }


}
