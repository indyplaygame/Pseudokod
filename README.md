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
- `set` (Alternatively: `zbior`)
- `range` (Alternatively: `przedzial`)'

**Available ranges (optional, only for numbers):**
- `N` - Natural numbers (Positive integers including zero).
- `N+` - Positive natural numbers (Positive integers excluding zero).
- `Z` - Integers.
- `Z+` - Positive integers.
- `Z-` - Negative integers.
- `Q` - Rational numbers.
- `(a, b)` - open interval (All numbers between `a` and `b` axcluding `a` and `b`).
- `(a, b]` - left-open interval (All numbers between `a` and `b` excluding `a` and including `b`).
- `[a, b)` - right-open interval (All numbers between `a` and `b` including `a` and excluding `b`).
- `[a, b]` - closed interval (All numbers between `a` and `b` including `a` and `b`).

**Available sets (optional, only for numbers):**
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
- Inequal (`≠`, `!=`).
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

**Alternatives to the 'get' statement:** `load`, `wczytaj`.

### 8. If statement
**Syntax:**
```
if condition
  instructions
  ...
```

**Alternatives to the 'if' statement:** `jezeli`, `jesli`.

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
for control_variable = 0, 1, 2, ..., n
  instructions
  ...
```

### 11. Functions
**Syntax:**
```
// Declaration
function_name:
  data:
    data_type variable_name (∈ range <- value);
    data_type variable1_name (∈ range <- value);
    ...
  results:
    variable_name: possible_values | range;

  instructions
  ...

// Usage
function_name(arg, arg1);
```

**Alternative to the 'data' keyword:** `dane`.<br>
**Alternative to the 'results' keyword:** `wyniki`.

### 12. Exceptions
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
MissingIdentifierException: Identifier expected in AssignmentExpression, but found: statement.
IllegalConditionException: 'true' condition in while loop is not allowed.
RecursionError: function cannot call itself.
```

### 13. Comments
**Syntax:**
```
// This is a one line comment
/*
This is a multi line comment
*/
```

### 14. Ending program
**Syntax:**
```
who understands?;
```

**Alternative to 'who understands?':** `kto rozumie?`.

## Tokens
