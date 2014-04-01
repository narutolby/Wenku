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
	function arrayToString($result_array) {
		reset($result_array);
		$s="";
		$itemNo=0;
		$total=count($result_array);
		while ($array_cell=each($result_array)) {
			$itemNo++;
			if ($itemNo<30) {
				$s .= $array_cell['value'] . chr(10) ;
			} else if ($itemNo>$total-30) {
				$s .= $array_cell['value'] . chr(10) ;
			} else if ($itemNo==30) {
				$s .= chr(10) . "... ... ... ... ... ... ... ... ... ..." . chr(10) . chr(10);
			}
		}
		return $s;
	}
	
	function getLastWord($myStr) {
		$compare=1;
		$i=0;
		while(($compare!=0)&&($i+strlen($myStr)>0)) {
			$i--;
			$s1=substr($myStr,$i,1);
			$compare=strcmp($s1,"/");
		}
		return substr($myStr,strlen($myStr)+$i);
	}

	function removeFileName($myStr) {
		$end=getLastWord($myStr);
		$root=substr($myStr,0,strlen($myStr)-strlen($end));
		if ($root{strlen($root)-1}!="/") $root=$root . "/";
		return $root;
	}
	
	function validSwfParams($path,$doc,$page){
		return !(	basename(realpath($path)) != $doc  . $page . ".swf" ||
				 	substr_compare($doc, 'pdf', -3, 3) === -1 ||
				 	strlen($doc) > 255 ||
				 	strlen($page) > 255
				);	
	}
	
	function validPdfParams($path,$doc,$page){
		return !(	basename(realpath($path)) != $doc ||
				 	substr_compare($doc, 'pdf', -3, 3) === -1 ||
				 	strlen($doc) > 255 ||
				 	strlen($page) > 255
				);	
	}
?>