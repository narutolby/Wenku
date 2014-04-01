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
require_once("pdf2swf_php5.php");

class swfextract
{
	private $configManager = null;
	private $pdftoolsPath;
	
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
        //echo "swfextract destructed\n";
    }
	
	/**
	* Method:search
	*/
	public function extractText($doc,$page)
	{
		$output=array();
	
		try {
			// check for directory traversal & access to non pdf files and absurdely long params
			if(	!validSwfParams($this->configManager->getConfig('path.swf') . $doc  . $page . ".swf",$doc,$page) )
				return;
		
			$command = $this->configManager->getConfig('cmd.searching.extracttext');
			$command = str_replace("{path.swf}",$this->configManager->getConfig('path.swf'),$command);
			$command = str_replace("{swffile}",$doc  . $page. ".swf",$command);
			$return_var=0;
			
			exec($command,$output,$return_var);
			if($return_var==0){
				return arrayToString($output);
			}else{
				return "[Error Extracting]";
			}
		} catch (Exception $ex) {
			return $ex;
		}
	}
}
?>