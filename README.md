# TimeMachine

This project is intended to be used in systems where time based features are implemented. With this lightweight project
you can mimic to change JVM time without affecting system clock, which can be helpful when you write automated tests.

## Prerequisites

You just need to include the following dependency into your project. This project does not have any further transitive
dependencies, so it's size is minimal.

```xml

<dependencies>
    ...
    <dependency>
        <groupId>com.szityu.oss.timemachine</groupId>
        <artifactId>time-machine</artifactId>
        <packaging>jar</packaging>
        <version>1.0.0</version>
    </dependency>
    ...
</dependencies>

``` 

## Usage

### In production code

The first and only rule about using TimeMachine is ..... to **USE** TimeMachine. :)

**ALWAYS** obtain dates & times through TimeMachine and **DO NOT** obtain them directly, as TimeMachine maintains the
traveled time privately and does not affect the system or JVM clock.

**Dates & times obtained directly from Java won't represent the traveled time.**

You can obtain time from `TimeMachine` by its static methods, e.g., `TimeMachine.zonedDateTimeOfNow()` .

### In tests

Simply use the static `travelAt` methods of `TimeMachine` class. Every date & time obtained through TimeMachine will
represent the traveled time. Don't forget to `reset` time after tests to avoid affecting each other.

## Author

* **Szilard Laszlo Fodor** - [Mr-DeWitt](https://github.com/Mr-DeWitt)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details