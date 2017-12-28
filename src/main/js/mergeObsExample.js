const Rx = require('rxjs/Rx');

console.log("Got here");

const oneThruFourObs = Rx.Observable.of(1,2,3, 4).map(x => x + '!!!');

const fiveThru1k = Rx.Observable.range(5, 1000);

const mergedObs = Rx.Observable.merge(fiveThru1k, oneThruFourObs);

fiveThru1k.subscribe(s => console.log(s));

mergedObs.subscribe(s => console.log(s));

const oneThruFourStr = Rx.Observable.of("one", "two", "three", "four");

Rx.Observable.zip(oneThruFourObs,
    oneThruFourStr,
    (x, s) => x + " " + s ).subscribe(r => console.log(r));

