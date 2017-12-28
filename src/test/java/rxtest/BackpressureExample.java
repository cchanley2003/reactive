package rxtest;

import org.junit.Test;
import rx.BackpressureOverflow;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BackpressureExample {

    public static void compute(Integer v) {
        try {
            System.out.println("compute integer v: " + v);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void compute(List<Integer> v) {
        try {
            System.out.println("compute integer v: " + v);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void compute(Observable<Integer> v) {
        v.observeOn(Schedulers.io()).subscribe(i -> {
            System.out.println("compute integer v: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
    }

    @Test
    public void coldObservable() throws InterruptedException {
        Observable.range(1, 1_000_000)
                .observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute);

        Thread.sleep(6000);
    }

    @Test
    public void hotObservableWithBuffer() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.buffer(1024).observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(source::onNext);
        Thread.sleep(6000);
    }

    @Test
    public void hotObservableWithSample() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.sample(100, TimeUnit.MILLISECONDS).observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(source::onNext);

        PublishSubject<Integer> source2 = PublishSubject.create();
        source2.throttleFirst(100, TimeUnit.MILLISECONDS).observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(source2::onNext);
        Thread.sleep(6000);
    }

    @Test
    public void hotObservableWithWindow() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.window(1024).observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(source::onNext);
        Thread.sleep(60000);
    }

    @Test
    public void hotObservableWithBackpressure() throws InterruptedException {
        PublishSubject<Integer> source = PublishSubject.create();

        source.onBackpressureBuffer(16, () -> {}, BackpressureOverflow.ON_OVERFLOW_DROP_OLDEST)
                .observeOn(Schedulers.computation())
                .subscribe(BackpressureExample::compute, Throwable::printStackTrace);
        IntStream.range(1, 1_000_000).forEach(source::onNext);
        Thread.sleep(600000);
    }
}
