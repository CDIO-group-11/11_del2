## file structure
To add a new language into the program a .lang file is to be used, these files must start with the language name (as written in `src/lang/Lang.java`) and end in .lang<br>
nothing lese is to be added in the file name.<br>
this file must then be placed in the folder `Lang/`<br>
this is the same location as this file is found.
<br><br>
## Language
- `declarator syntax` first you must declare your declarator, as the lang file allows for the writer/user to choose the symbols that denominate different parts of the program, of course defaults are written in. <br>To use defaults either leave a line blank to ignore all configuration on all options on that line or leave a part blank to use default for that option.<br>if only the first  option is to be used all spaces must still be written!

  - `tile`<br>
  default = '<span style="color:lightgreen">@</span>'<br>
  described on line 1 word 1<br>
  default = '11'<br>
  number of tiles (as number) line 1 word 2
  - `User interface`<br>
  default = '<span style="color:lightgreen">&</span>'<br>
  described on line 1 word 3
  - `error message`<br>
  described on line 2<br>
  default = '<span style="color:lightgreen">[</span>'<br>
  char 1 = start<br><br>
  default = '<span style="color:lightgreen">]</span>'<br>
  char 2 = end

- `tiles` all tiles must be written from line 3.
  - the first char of a new tile must be the correct declarator
  - each tile must have it's associated number written right after the declarator
  - then a space followed by the flavour text given to that tile, any use of the declarator will be replaced by the value of the given tile
- `User interface` all interface parts must be written right after the last tile
  - each line of the interface must start with the declarator
  - then the color of the text, note that no space should be used between declarator and color, to use the consoles default color leave this blank  and instead add a space.
  - after the color add a space, this will then bbe written to the screen exactly as you see it note that each line in the file does not give a new line in the program
  - after the declarator any text written will be outputted exactly as written, with a few exceptions
    - all unicode characters between U+E000 and U+EFFF are reserved.
    - all spaces before or after tags will be removed, to place a space around them use two spaces<br>

    - the discriminator will not be treated like a new line, instead it allows for use of tags which are described in the table bellow. 
  - | **tags**                    |*`actions`*|*internal name*|
    |-----------------------------|-----------|---------------|
    | new line                    |`&&       `|*\n*           |
    | current player number       |`&player  `|*\uE001*       |
    | previous player number      |`&other   `|*\uE00E*       |
    | value of the first die      |`&die1    `|*\uE002*       |
    | value of the second die     |`&die2    `|*\uE003*       |
    | sum of dice                 |`&sum     `|*\uE004*       |
    | current player's total gold |`&gold    `|*\uE005*       |
    | player 1's total gold       |`&gold1   `|*\uE006*       |
    | player 2's total gold       |`&gold2   `|*\uE007*       |
    | turn number                 |`&turn    `|*\uE008*       |
    | current tile text           |`&tileT   `|*\uE009*       |
    | current tile nr             |`&tileNR  `|*\uE00A*       |
    | change text color red       |`&red     `|*Ansi.r()*     |
    | change text color red       |`&r       `|*Ansi.r()*     |
    | change text color green     |`&green   `|*Ansi.g()*     |
    | change text color green     |`&g       `|*Ansi.g()*     |
    | change text color blue      |`&blue    `|*Ansi.b()*     |
    | change text color blue      |`&b       `|*Ansi.b()*     |
    | change text color cyan      |`&cyan    `|*Ansi.c()*     |
    | change text color cyan      |`&c       `|*Ansi.c()*     |
    | change text color yellow    |`&yellow  `|*Ansi.y()*     |
    | change text color yellow    |`&y       `|*Ansi.y()*     |
    | change text color purple    |`&purple  `|*Ansi.p()*     |
    | change text color purple    |`&p       `|*Ansi.p()*     |
    | use default color           |`&reset   `|*Ansi.reset()* |
    | use default color           |`&default `|*Ansi.reset()* |
    | use default color           |`&        `|*Ansi.reset()* |
    | roll command                |`&roll    `|*\uE00B*       |
    | save command                |`&save    `|*\uE00C*       |
    | exit command                |`&exit    `|*\uE00D*       |
    | writing point               |`&input   `|*\n*           |
  - lastly `&input&` may exist exactly once, this tag shows where the user will write their input, it will automatically add a new line after it, no new line tag should be written on the same line as the input tag.
- `error messages`
  - these are not strictly necessary and are only meant for debugging and should not (unless something goes wrong) be printed, they are only meant to allow further development using your translated error messages
  - each line must start with the start descriptor
  - without adding a space the error name must be written
  - then the end descriptor followed by a space
  - the get the stacktrace write the end and start descriptor without anything in the middle.

<br>
#TODO file name / save gold