Agenda
- Miscellany
    - logging
    - lambdas and functions
    - streams
    - reflection

Logging
- Many different logging libraries exist
    - Java logging
    - Commons logging (Apache)
    - SLF4J
    - Log4j
    - Logback

Which one to choose?!
- SLF4J is a great general purpose logging that adapts to underlying loggers
- Provides a consistent logging API that your code implements to
- Simply adding implementation dependency adds the underlying logger
- Underlying logger is configured in its own way

Example: SLF4J and Log4j
- slf4j-api
- slf4j-log4j12
- log4j.properties file configures Log4j

What are Lambdas

What are Functions

What is Reflection?
- Class metadata
- The class Class<>
    - Methods to interrogate everything about a class
    - Constructors, methods, annotations, etc

Example: Invoking a method on an object without knowing the class of the object
- java.lang.Object#getClass()
- java.lang.Class#getMethod()
- java.reflect.Method#invoke()

Example: Checking if a class has an annotation
- java.lang.reflect.AnnotatedElement#isAnnotationPresent() 

Fast classpath scanner
- Third party library
- https://github.com/lukehutch/fast-classpath-scanner

Example: Locate all classes with annotation

        new FastClasspathScanner("edu.wctc.dj")
                .matchClassesWithAnnotation(Scenario.class,
                    c -> { /* found one */ });

What are Streams?
- Classes and methods to support functional-style operations on streams of elements
- What can you do with streams?
