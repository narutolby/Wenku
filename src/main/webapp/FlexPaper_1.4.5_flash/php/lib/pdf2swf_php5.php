<?php
/**
* █▒▓▒░ The FlexPaper Project 
* 
* Copyright (c) 2009 - 2011 Devaldi Ltd
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

require_once("config.php");
require_once("common.php");

class pdf2swf
{
	private $configManager = null;

	/**
	* Constructor
	*/
	function __construct()
	{
		$this->configManager = new Config();
	}

	/**
	* Destructor
	*/
	function __destruct() {
        //echo "pdf2swf destructed\n";
    	}

	/**
	* Method:convert
	*/
	public function convert($doc,$page)
	{
		$output=array();
		$pdfFilePath = $this->configManager->getConfig('path.pdf') . $doc;
		$swfFilePath = $this->configManager->getConfig('path.swf') . $doc  . $page. ".swf";
		
		// traversal and file name check
		if(	!validPdfParams($pdfFilePath,$doc,$page) ) {
			$s="Incorrect document file specified, file may not exist or insufficient permissions to read file" . $configManager->getDocUrl();
			return $s;
		}
		
		if(strlen($page)>0)
			$command = $this->configManager->getConfig('cmd.conversion.splitpages');
		else
			$command = $this->configManager->getConfig('cmd.conversion.singledoc');
			
		$command = str_replace("{path.pdf}",$this->configManager->getConfig('path.pdf'),$command);
		$command = str_replace("{path.swf}",$this->configManager->getConfig('path.swf'),$command);
		$command = str_replace("{pdffile}",$doc,$command);
		
		try {
			if (!$this->isNotConverted($pdfFilePath,$swfFilePath)) {
				array_push ($output, utf8_encode("[Converted]"));
				return arrayToString($output);
			}
		} catch (Exception $ex) {
			array_push ($output, "Error," . utf8_encode($ex->getMessage()));
			return arrayToString($output);
		}

		$return_var=0;
		exec($command,$output,$return_var);
		if($return_var==0){
			$s="[Converted]";
		}else{
			$s="Error converting document, make sure the conversion tool is installed and that correct user permissions are applied to the SWF Path directory" . $configManager->getDocUrl();
		}
		return $s;
	}

	/**
	* Method:isConverted
	*/
	public function isNotConverted($pdfFilePath,$swfFilePath)
	{
		if (!file_exists($pdfFilePath)) {
			throw new Exception("Document does not exist");
		}
		if ($swfFilePath==null) {
			throw new Exception("Document output file name not set");
		} else {
			if (!file_exists($swfFilePath)) {
				return true;
			} else {
				if (filemtime($pdfFilePath)>filemtime($swfFilePath)) return true;
			}
		}
		return false;
	}
}
?>
