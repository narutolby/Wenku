<?php
/**
* █▒▓▒░ The FlexPaper Project 
* 
* Copyright (c) 2009 - 2011 Devaldi Ltd
* 
* Document page counter file for PHP. Accepts parameters 'doc'
* Executes specified conversion command if the document has not yet been
* converted and returns the number of pages in the converted document 
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

require_once("../lib/config.php");
require_once("../lib/common.php");
require_once("../lib/pdf2swf_php5.php");

$configManager = new Config();
$doc=$_GET["doc"];
$page = "1";
$swfFilePath = $configManager->getConfig('path.swf') . $doc  . $page. ".swf";
$pdfFilePath = $configManager->getConfig('path.pdf') . $doc;
$output = "";

if(glob($configManager->getConfig('path.swf') . $doc . "*")!=false)
	$pagecount = count(glob($configManager->getConfig('path.swf') . $doc . "*"));
else
	$pagecount = 0;

if($pagecount == 0 && validPdfParams($pdfFilePath,$doc,$page)){
	$pdfconv=new pdf2swf();
	$output=$pdfconv->convert($doc,$page);
	
	if(rtrim($output) === "[Converted]")
		$pagecount = count(glob($configManager->getConfig('path.swf') . $doc . "*"));
}else{
	$output = "Incorrect document file specified, file may not exist or insufficient permissions to read file" . $configManager->getDocUrl();
}

if($pagecount!=0)
	echo $pagecount;
else
	echo $output;
?>