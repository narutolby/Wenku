<?php 
/**
* █▒▓▒░ The FlexPaper Project 
* 
* Copyright (c) 2009 - 2011 Devaldi Ltd
*
* SWF text extraction for PHP. Executes the specified text extraction 
* executable and returns the output 
*   
* GNU GENERAL PUBLIC LICENSE Version 3 (GPL).
* 
* The GPL requires that you not remove the FlexPaper copyright notices
* from the user interface. 
*  
* Commercial licenses are available. The commercial player version
* does not require any Flowplayer notices or texts and also provides
* some additional features.
* When purchasing a commercial license, its terms substitute this license.
* Please see http://flexpaper.devaldi.com/ for further details.
* 
*/
require_once("../lib/swfextract_php5.php");
$doc=$_GET["doc"];
$page=$_GET["page"];
$searchterm=$_GET["searchterm"];

$swfextract=new swfextract();
echo strpos(strtolower($swfextract->extractText($doc,$page)),strtolower($searchterm));	
?>