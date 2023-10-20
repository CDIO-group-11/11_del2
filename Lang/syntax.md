## file structure
To add a new language into the program a .lang file is to be used, these files must start with the language name (as written in src/lang/Lang.java) and end in .lang<br>
nothing lese is to be added in the file name.<br>
this file must then be placed in the folder Lang/<br>
this is the same location as this file is found.
<br><br>
## Language
- `declare syntax` first you must declare your syntax, as the lang file allows for the writer/user to choose the symbols that denominate different parts of the program, ofcourse deafults are written in. <br>To use defaults either leave a line blank to ignore all configuration on all options on that line or add spaces instead of the specific char to ignore it's option.
  - `tile`<br>
  default = '@'<br>
  described on line 1 char 1
  - `User inteface`<br>
  default = '&'<br>
  described on line 1 char 2
  - `error message`<br>
  default = [error name]<br>
  described on line 2<br>
  char 1 = start<br>
  char 2 = end
- `tiles` all tiles must be written from line 3, 


#TODO file name<br>
#TODO gemme guld