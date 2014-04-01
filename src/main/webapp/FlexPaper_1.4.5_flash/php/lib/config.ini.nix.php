; <?php exit; ?> DO NOT REMOVE THIS LINE
[general]
 path.pdf = "/var/www/flexpaper/php/pdf/"
 path.swf = "/var/www/flexpaper/php/docs/"
 
[external commands]
 cmd.conversion.singledoc = "/usr/bin/pdf2swf {path.pdf}{pdffile} -o {path.swf}{pdffile}.swf -f -T 9 -t -s storeallcharacters"
 cmd.conversion.splitpages = "/usr/bin/pdf2swf {path.pdf}{pdffile} -o {path.swf}{pdffile}%.swf -f -T 9 -t -s storeallcharacters -s linknameurl"
 cmd.searching.extracttext = "/usr/bin/swfstrings {path.swf}{swffile}"
