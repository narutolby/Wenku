; <?php exit; ?> DO NOT REMOVE THIS LINE
[general]
 path.pdf = "C:\inetpub\wwwroot\flexpaper\php\pdf\"
 path.swf = "C:\inetpub\wwwroot\flexpaper\php\docs\"
 
[external commands]
 cmd.conversion.singledoc = "\"C:\Program Files\SWFTools\pdf2swf.exe\" {path.pdf}{pdffile} -o {path.swf}{pdffile}.swf -f -T 9 -t -s storeallcharacters"
 cmd.conversion.splitpages = "\"C:\Program Files\SWFTools\pdf2swf.exe\" {path.pdf}{pdffile} -o {path.swf}{pdffile}%.swf -f -T 9 -t -s storeallcharacters -s linknameurl"
 cmd.searching.extracttext = "\"C:\Program Files\SWFTools\swfstrings.exe\" {path.swf}{swffile}"
