# Kotlinslang
A functional library for Kotlin that provides  functional control structures. Inspired by Javaslang and funKTionale.   
     
[![Build Status](https://travis-ci.org/kotlinslang/kotlinslang.svg?branch=master)](https://travis-ci.org/kotlinslang/kotlinslang)
[![codecov.io](https://codecov.io/github/kotlinslang/kotlinslang/coverage.svg?branch=master)](https://codecov.io/github/kotlinslang/kotlinslang?branch=master) 

## Introduction
Kotlinslang is a functional library for kotlin that provides functional (Monadic) control structures such as `Try<T>`, `Option<T>` and `Either<L,R>` also helpers for functional programming.
Heavily inspired by [Javaslang](http://www.javaslang.io/) and [funKTonale](https://github.com/MarioAriasC/funKTionale).

## Usage
For now Kotlinslang library available at bintray.com.
  
### Gradle
You must configure your `gradle.build` to use specific maven repository from bintray.      
```    
repositories {
    mavenCentral()
    maven { url "http://dl.bintray.com/jasoet-gdp/org.kotlinslang" }
}
```   
       
Then you can use Kotlinslang dependency.   
```   
dependencies {
    compile "org.kotlinslang:kotlinslang:1.0.2"
}  
```   

### Maven
You must configure your `pom.xml` to use specific maven repository from bintray.      
```xml
<repository>
    <id>bintray</id>
    <name>bintray</name>
    <url>http://dl.bintray.com/jasoet-gdp/org.kotlinslang</url>
</repository>
```     

Then add the dependency     
```xml
<dependency>
    <groupId>org.kotlinslang</groupId>
    <artifactId>kotlinslang</artifactId>
    <version>1.0.2</version>
</dependency>   
```    

