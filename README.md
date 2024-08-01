# LoggerInstrumentation

The goal of this project is to identify use of Log4j library. This is for instrumenting an existing application identifying Log4j logging methods, extracting details about the logging calls, and evaluate the performance impact of such an instrumentation.

For now the the class name is harcoded but could be improved with matcher any

Below are the points it is covering

Instrument Log4j Log Methods: Modify the bytecode to intercept calls to Log4j logging methods (e.g., Logger.info(), Logger.error(), etc.).

Extract Log Level and Arguments: Capture and display the log level (e.g., INFO, DEBUG, ERROR) and any arguments passed to the logging method.

Extract Code Location: Determine and display the location in the code where the logging call was made. This should include at least the class name, method name, and line number. Example of minimum expected result: main.java:12 INFO

Instrument toString Method: Instrument the toString method on all objects to ensure detailed state information is available when objects are passed to the logger. Example of expected result: main.java:12 aClass.toString => "result of the toString method"

