# Pseudokod (.pk)
Pseudokod's adaptation as a programming language.

## Installation

## Visual Studio Code Extension

## Usage
### 1. Declaring variables
**Syntax:**
```
data:
  data_type variable_name (∈ range/set <- value),
  data_type variable1_name (∈ range/set := value)

dane:
  data_type variable_name (∈ range/set <- value),
  data_type variable1_name (∈ range/set := value)
```

**Available data types:**
- `number` (Alternatively: `liczba`).
- `char` (Alternatively: `znak`).
- `string` (Alternatively: `tekst`).
- `boolean` (Alternatively: `logiczny`).
- `list` (Alternatively: `tablica`).
- `set` (Alternatively: `zbior`).
- `range` (Alternatively: `przedzial`).
- `plate` (Alternatively: `talerz`, commonly known as stack, Last-In-First-Out).
- `queue` (Alternatively: `kolejka`, First-In-First-Out).

**Available ranges (optional, only for numbers):**
- `Q` - Rational numbers.
- `(a, b)` - open interval (All numbers between `a` and `b` excluding `a` and `b`).
- `(a, b]` - left-open interval (All numbers between `a` and `b` excluding `a` and including `b`).
- `[a, b)` - right-open interval (All numbers between `a` and `b` including `a` and excluding `b`).
- `[a, b]` - closed interval (All numbers between `a` and `b` including `a` and `b`).

**Available sets (optional, only for numbers):**
- `N` - Natural numbers (Positive integers including zero).
- `N+` - Positive natural numbers (Positive integers excluding zero).
- `Z` - Integers.
- `Z+` - Positive integers.
- `Z-` - Negative integers.
- `{0, 1, 2}` - set of numbers.
- `{0, 1, ..., n}` - set of numbers.

**Constant numbers**:
- infinity (Alternatively: `nieskonczonosc`, `∞`) - `9,223,372,036,854,775,807`.
- pi (Alternatively: `π`) - `3.141592653589793`.

**Alternatives to the '∈' sign:** `belongs`, `in`, `nalezy`, `w`.

> [!WARNING]
> **Important note about lists in pseudokod: list indexes start from 1.**

### 2. Assigning values to variables
**Syntax:**
```
variable_name <- value;
variable_name := value;
```

### 3. Comparison operators
**Available comparison operators:**
- Equal (`=`).
- Unequal (`≠`, `!=`).
- Less than (`<`).
- Less or equal (`≤`, `<=`).
- Greater than (`>`).
- Greater or equal (`≥`, `>=`).

### 4. Logical operators
**Available logical operators:**
- Conjunction (`AND`, `I`, `∧`).
- Alternative (`OR`, `LUB`, `∨`).
- Negation (`NOT`, `NIE`, `¬`).
- Exclusive disjunction (`XOR`, `⊕`).

### 5. Bitwise operators
**Available bitwise operators:**
- Bitwise NOT (`~`).
- Bitwise AND (`&`).
- Bitwise OR (`|`).
- Bitwise XOR (`^`).
- Bitwise left shift (`<<`).
- Bitwise right shift (`>>`).

### 6. Arithmetical operators
**Available arithmetical operators:**
- Addition (`+`).
- Subtraction (`-`).
- Multiplication (`*`).
- Division (`/`).
- Integer division (`div`).
- Modulus (`mod`).

### 7. Outputting data to the user
**Syntax:**
```
print "text";
print variable_name;
```

**Alternatives to the 'print' statement:** `write`, `wypisz`, `drukuj`, `wyprintuj`.

### 8. Getting data from the user.
**Syntax:**
```
get variable_name;
```

**Alternatives to the 'get' statement:** `load`, `wczytaj`, `wprowadz`, `input`.

### 9. If statement
**Syntax:**
```
if condition
  instructions
  ...
else if condition
  instructions
  ...
else
  instructions
  ...
```

**Alternatives to the 'if' keyword:** `jezeli`, `jesli`.<br>
**Alternatives to the 'else' keyword:** `przeciwnie`.

### 10. While loop (Do-while)
**Syntax:**
```
// While loop
while condition
  instructions
  ...

// Do-while loop
do
  instructions
  ...
while condition;
```
**Alternatives to the 'for' keyword:** `dla`.<br>
**Alternatives to the 'do' keyword:** `rob`.<br>
**Alternatives to the 'while' keyword:** `dopoki`.<br>

### 11. For loop
**Syntax:**
```
for control_variable = 0, 1, ..., n
  instructions
  ...
```

### 12. Functions
**Syntax:**
```
// Declaration
function function_name:
  data:
    data_type variable_name (∈ range <- value);
    data_type argument_name* (∈ range <- value);
    ...
  result:
    data_type (variable_name): possible_values | range;

  instructions
  ...
  return result;

// Usage
function_name(arg, arg1);
```
**Alternative to the 'function' keyword:** `funkcja`.<br>
**Alternative to the 'data' keyword:** `dane`.<br>
**Alternative to the 'results' keyword:** `wynik`.<br>
**Alternative to the 'return' keyword:** `zwroc`.

### 13. Built-in functions

**List of built-in functions:**
 - `date()` - Returns current date as string in format 'DD-MM-YYYY'.
 - `time()` - Returns current time as string in format 'HH:MM:SS'. (Alternatively: `czas`)
 - `random()` - Returns random integer between `1` and `2,147,483,647`. (Alternatively: `losuj`)
 - `wait(number n)` - Waits `n` milliseconds before executing the rest of the code. (Alternatively: `delay`, `czekaj`)
 - `push(plate p, any n)` - Adds a pancake (element) with value `n` to the `p` plate (stack). (Alternatively: `pchnij`)
 - `push(queue q, any n)` - Adds an element with value `n` to the `q` queue. (Alternatively: `pchnij`)
 - `pop(plate p)` - Removes a pancake (element) from the top of the plate `p` and returns its value. (Alternatively: `puknij`)
 - `pop(queue q)` - Removes the first element from the queue `q` and returns its value. (Alternatively: `puknij`)
 - `size(plate p)` - Returns the amount of pancakes on the plate `p`. (Alternatively: `rozmiar`)
 - `size(queue q)` - Returns the size of queue `q`. (Alternatively: `rozmiar`)
 - `empty(plate p)` - Returns `true` if there are no pancakes on the plate `p` and `false` if there are any pancakes. (Alternatively: `pusty`)
 - `empty(queue q)` - Returns `true` if queue `q` is empty and `false` if it's not empty. (Alternatively: `pusty`)
 - `top(plate p)` - Returns the value of pancake from the top of the plate `p`. (Alternatively: `szczyt`)
 - `front(queue q)` - Returns the value of the first element of the queue `q`. (Alternatively: `poczatek`)

### 14. Exceptions
**Possible exceptions:**
```
UnrecognizedCharacterException: Character 'character' is unrecognizable and cannot be converted to Token.
UnexpectedTokenException: Unexpected token found during parsing: {value: "value", type: type}.
MissingTokenException: Unexpected token found. Expected 'type' token, found 'type'.
ASTNodeNotSetupException: This AST node has not yet been setup for interpretation: type.
DivisionByZeroException: Division by zero is not allowed.
VariableDeclaredException: Cannot declare a new variable: variable named 'name' already exists.
VariableNotDeclaredException: Cannot assign a value to variable: variable named 'name' was not declared in the scope.
IllegalDataTypeException: Data type 'type' is not allowed here.
MissingIdentifierException: Identifier expected in 'expression', but found: statement.
DataTypeMismatchException: Data type 'type' was expected here, but received 'type'.
IncompatibleDataTypesException: Cannot compare left to right, incompatible data types.
ConstantAssignmentException: Cannot assign value to 'name' because it is a constant.
StringTerminationException: Unterminated string found, expected the 'character' character to terminate the string.
CharactersAmountException: Expected single character (found 'amount' of them).
IllegalIndexTypeException: Identifier or NumericLiteral expected as list index, received 'type'.
ConversionDataTypeException: 'Type' cannot be converted to 'type'.
IndexOutOfRangeException: Index index out of range for length length.
InvalidSetSyntaxException: Invalid set syntax, please check the documentation for valid set syntax.
InvalidCallableException: type is not callable.
ArgumentsAmountException: Expected amount arguments, but received amount.
IllegalExpressionStartException: Illegal start of expression, expected 'type'.
InvalidExpressionException: Expected 'type' expression, but received 'received'.
UnexpectedNodeException: Node type was not expected here.
NumberOutOfRangeException: Number number is out of range range.
IncorrectFunctionDeclarationSyntaxException: Unexpected token found inside function declaration statement.
IllegalConditionException: 'true' condition in while loop is not allowed.
InvalidWhileLoopExpressionException: While loop expression is expected to return boolean value, but returned 'received' instead.
RecursionError: Function cannot call itself.
```

### 15. Comments
**Syntax:**
```
// This is a one line comment
/*
This is a multi line comment
*/
```

### 16. Ending program
**Syntax:**
```
who understands?;
```

**Alternative to 'who understands?':** `kto rozumie?`.

### 17. Using external libraries
**Syntax:**
```
import file_path;
```

**Alternative to 'import' keyword:** `zaimportuj`.

## Tokens

## Nodes

## Reserved keywords

## Environmental variables

## Built-in libraries