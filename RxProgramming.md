Reactive Programing
===================

History
----------
Originally developed by Microsoft. First introduction was in C#. Now supported by the following languages:

- Java: RxJava
- JavaScript: RxJS
- C#: Rx.NET
- C#(Unity): UniRx
- Scala: RxScala
- Clojure: RxClojure
- C++: RxCpp
- Lua: RxLua
- Ruby: Rx.rb
- Python: RxPY
- Go: RxGo
- Groovy: RxGroovy
- JRuby: RxJRuby
- Kotlin: RxKotlin
- Swift: RxSwift
- PHP: RxPHP
- Elixir: reaxive
- Dart: RxDart

## Motivations

Async programming maximizes resource utiliztion
NodeJS has excellent performance despite being single threaded because nothing waits
Observables are a solution for "callback hell"

##Streams vs RxJava
- Observables can do either pull or push streams are pull only
- Observables sort additional operations (streams getting closer with jdk9)
- Observables are part of the jdk9 spec.
- Finer grained thread control
- Support for back pressure

##Promises vs RsJs

- Observables handle streams while a Promise is a single event.
- Can handle 0, 1, or Multiple Events
- Provides map, reduce, filter
- Observables can be replayed
- Observables can be throttled or sampled via back pressure
- Observables are cancelable

Advantages
==========
##Lazy

Computations only occur when a subscription occurs
##Composition

- Chaining 
- Merging
- Zipping
- And/Then/When
- Switch

##Threading Control

### Observer on

Observe on effects all downstream actions

### Subscribe on

Effects the the thread that items are emitted on

### Types of Schedulers
- Computational -- matches number of processors
- IO - can grow and shrink to accommodate
- Use your own thread pool
- Use a new thread every time - not advised
- Immediate - uses the current thread
- Default - JS only uses callback thread

##Error Handling

- Separate channel for errors
- Errors end the current stream 
- Stream can be restarted from errors
##Back pressure

Back pressure is the concept of how to handle data that exceeds your computational rate

Various strategies out of the box
- Buffering
- Windows
- Sampling
- Throttling
- Can define your own strategies

-------------

