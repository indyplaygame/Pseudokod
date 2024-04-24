# Pseudokod (.pk)
Pseudokod's adaptation as a programming language.

## General Assumptions
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
- Negation (`NOT`, `NIE`, `¬`, `~`).

### 5. Arithmetical operators
**Available arithmetical operators:**
- Addition (`+`).
- Subtraction (`-`).
- Multiplication (`*`).
- Division (`/`).
- Integer division (`div`).
- Modulus (`mod`).

### 6. Outputting data to the user
**Syntax:**
```
print "text";
print variable_name;
```

**Alternatives to the 'print' statement:** `write`, `wypisz`.

### 7. Getting data from the user.
**Syntax:**
```
get variable_name;
```

**Alternatives to the 'get' statement:** `load`, `wczytaj`, `wprowadz`, `input`.

### 8. If statement
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

### 9. While loop (Do-while)
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

### 10. For loop
**Syntax:**
```
for control_variable = 0, 1, ..., n
  instructions
  ...
```

### 11. Functions
**Syntax:**
```
// Declaration
function function_name:
  data:
    data_type variable_name (∈ range <- value);
    data_type variable1_name (∈ range <- value);
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

### 12. Built-in functions

**List of built-in functions:**
 - `date()` - Returns current date as string in format 'DD-MM-YYYY'.
 - `time()` - Returns current time as string in format 'HH:MM:SS'. (Alternatively: `czas`)
 - `random()` - Returns random integer between `1` and `2,147,483,647`. (Alternatively: `losuj`)
 - `wait(int n)` - Waits `n` milliseconds before executing the rest of the code. (Alternatively: `delay`, `czekaj`)

### 13. Exceptions
**Possible exceptions:**
```
UnrecognizedCharacterException: Character 'character' is unrecognizable and cannot be converted to Token.
UnexpectedTokenException: Unexpected token found during parsing: {value: "value", type: type}.
MissingTokenException: Unexpected token found. Expected 'type' token, found 'type'.
ASTNodeNotSetupException: This AST node has not yet been setup for interpretation: type.
DivisionByZeroException: Division by zero is not allowed.
VariableDeclaredException: Cannot declare a new variable: variable named 'name' is already defined.
VariableNotDeclaredException: Cannot assign a value to variable: variable named 'name' does not exist.
IllegalDataTypeException: Data type 'type' is not allowed here.
MissingIdentifierException: Identifier expected in 'expression', but found: statement.
IncompatibleDataTypeException: Data type 'type' was expected here, but received 'type'.
ConstantAssignmentException: Cannot assign value to 'name' because it is a constant.
StringTerminationException: Unterminated string found, expected the 'character' character to terminate the string.
CharactersAmountException: Expected single character (found 'amount' of them).
IllegalIndexTypeException: Identifier or NumericLiteral expected as list index, received 'type'.
InvalidConversionDataTypeException: 'Type' cannot be converted to 'type'.
IndexOutOfRangeException: Index index out of range for length length.
InvalidSetSyntaxException: Invalid set syntax, please check the documentation for valid set syntax.
InvalidCallableException: type is not callable data type.
ArgumentsAmountException: Expected amount arguments, but received amount.
IllegalExpressionStartException: Illegal start of expression, expected 'type'.
MissingExpressionException: Expected 'type' expression, but received 'received'.
UnexpectedNodeException: Node type was not expected here.
NumberOutOfRangeException: Number number is out of range range.
IncorrectFunctionDeclarationSyntaxException: Unexpected token found inside function declaration statement.
IllegalConditionException: 'true' condition in while loop is not allowed.
RecursionError: Function cannot call itself.
```

### 14. Comments
**Syntax:**
```
// This is a one line comment
/*
This is a multi line comment
*/
```

### 15. Ending program
**Syntax:**
```
who understands?;
```

**Alternative to 'who understands?':** `kto rozumie?`.

### 16. Using external libraries
**Syntax:**
```
import file_path;
```

**Alternative to 'import' keyword:** `zaimportuj`.

## Tokens