
# Programming Language Written from reading "Crafting Interpreters"

## Attempts (Lessons Learned):
### Attempt 1: Failure!!
1. Wrote (very few) characterization tests. These failed to capture all the behaviour of the application.
2. As a result of not having all the behaviour captured, as I rewrote some classes multiple functions/workings of the program broke
3. Then tried to add more characterization tests + fix the behaviour at the same time
4. Result: Disaster!!! An unmanageable codebase, decided eventually to throw it out and start where I had left off from the book.

### Attempt 2: Success!!
1. Wrote about 20-30 characterization tests. Mostly captured the behaviour.
2. Realized the program/code is half done, has some of my edits, updated it to only be the books code
3. Updated my tests to only be testing the books code with characterization tests
4. 55 characterization tests written, program/code behaves as expected/works!, and I have a bunch of tests that explain the code
5. Want to add sociable/more comprehensive testing suite
6. Eventually split apart classes as was my original goal in Attempt 1, or clarify some classes or structures (coaching should help here!)

## Class Structure/Files Short Description on my understanding of what they do:
AstPrinter: can print a statement or expression. Highly useful for testing
Environment: does environment things, I think mostly for figuring out how fast things run
Interpreter: "Interprets" a list of expressions or statements
Lox:: the main class that starts the program, runs the file, and then calls the scanner, parser, and interpreter as needed.
LoxCallable: not sure
LoxClass: something to do with lox classes
LoxFunction: something to do with lox functions
LoxInstance: probably for instance variables (not tested yet actually... ?)
Parser: parses tokens from the scanner to give to the interpreter
Resolver: Seems to call the interpreter and statements/expressions directly for some reason
Return: Something to do with returning values from functions/class methods likely
Scanner: Scans an input and then produces a series of tokens for the parser->interpreter
Stmt: something that is executable (not certain on that)
Token: a single unit to be parsed or interpreted/run/executed/etc.
TokenType: the thing in the token, its the type, could be almost anything, eg: keyword, string, number, division, etc.

## Running the program
download the program, and run the main method, it will automatically run what is essentially a repl for lox. Can also 
directly run files but I haven't done/used that portion of the programming language yet

## Tests
There are 55 tests so far, mostly unit, some "acceptance style" but those are very sparing. Mostly tested the scanner, parser, 
and interpreter so far.