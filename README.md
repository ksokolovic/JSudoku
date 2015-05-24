# JSudoku
* * *

**JSudoku** is a Java-based Sudoku library. It provides a framework with various Sudoku related algorithms. 

The library provides the following classes:

* Model: A class that models a Sudoku puzzle grid. 
* Generator: A class that generates the new Sudoku puzzle based on the given level of difficulty.
* Solver: A class that implements various algorithms for solving the Sudoku puzzle. The following algorithms are provided:
    * Row Elimination Technique
    * Column Elimination Technique
    * Minigrid Elimination Technique
    * Row Lone Rangers Technique
    * Column Lone Rangers Technique
    * Minigrid Lone Rangers Technique
    * Row Twins Technique
    * Column Twins Technique
    * Minigrid Twins Technique
    * Row Triplets Technique
    * Column Triplets Technique
    * Minigrid Triplets Technique
    * Brute Force Elimination Technique

## Documentation

The documentation can be reached at the [wiki page](https://bitbucket.org/sokolovic/jsudoku/wiki).

# Usage
* * *

To be able to use the library, you can clone the source code from the repository:

```sh
$ git clone https://sokolovic@bitbucket.org/sokolovic/jsudoku.git
```

You can build a library JAR by running the *Ant* as follows (assuming you're on the same directory level where the ```build.xml``` file resides):

```sh
$ ant
```

After getting the information on successful build, the target JAR is in the ```/build/jar/``` subdirectory of the root project directory. 

# Developers
* * *

| Name            | E-mail address                       | Skype ID            |
|:----------------|:------------------------------------:|:-------------------:|
| Kemal Sokolović | kemal DOT sokolovic AT gmail DOT com | kemal DOT sokolovic |


## Maintainers

| Name            | E-mail address                       | Skype ID            |
|:----------------|:------------------------------------:|:-------------------:|
| Kemal Sokolović | kemal DOT sokolovic AT gmail DOT com | kemal DOT sokolovic |


## Contributors

In case you discover any bugs or want to contribute to the project in any way, feel free to raise an issue [here](https://bitbucket.org/sokolovic/jsudoku/issues). You can also join publicly available [HipChat channel](https://www.hipchat.com/gVvvyStse) for a discussion. 

| Name            | E-mail address                       | Skype ID            |
|:----------------|:------------------------------------:|:-------------------:|
|                 |                                      |                     |

# Licence
* * *

> *Copyright (c) 2014-2015 Kemal Sokolović <kemal DOT sokolovic AT gmail DOT com>*
>
> Permission is hereby granted, free of charge, to any person obtaining a copy of
> this software and associated documentation files (the "Software"), to deal in the
> Software without restriction, including without limitation the rights to use,
> copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
> Software, and to permit persons to whom the Software is furnished to do so,
> subject to the following conditions:
>
> The above copyright notice and this permission notice shall be included in all
> copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
> IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
> FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
> IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
> DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
> ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
> DEALINGS IN THE SOFTWARE.