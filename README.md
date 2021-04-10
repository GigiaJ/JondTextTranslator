A program designed to take an excel sheet filled with commands created by Jon and find their corresponding text matches in text dump files based on the respective Pokemon game
The reason for doing so is that translating manually would be a herculean task and doing more than a single language would be virtually impossible without Jon spending 
months just translating. The program will have more functionality soon, but currently has predetermined directories and files that are just selected rather than providing
the files and such as arguments or input.

INSTRUCTIONS:
To load the project yourself pick your favorite IDE (IntelliJ/Eclipse are all I've used for Java so) and import the project from git and then select clone URI/existing URI.
Once the project imports it'll also download the dependencies listed in the gradle file (No funky Maven tests, but I should probably look into unit testing)
Then once everything is loaded go in and modify the file path for the command excel sheet, text dumps, and output file.
Run the program and the output should be where you designated.

I doubt anyone beyond Jon would ever have a use for this. It was quickly written so it could algorithmically be more efficient, but since it won't need to be ran
more than around a few dozen times that isn't really relevant. It also contains some useful methods and patterns for parsing command block commands (especially text entries).