<?php
class Config{
  protected $config;
 
    public function __construct()
    {
		if(	PHP_OS == "WIN32" || PHP_OS == "WINNT"	)
      		$this->config = parse_ini_file('config.ini.win.php');
		else
			$this->config = parse_ini_file('config.ini.nix.php');
    }
 
    public function getConfig($key = null)
    {
      if($key !== null)
      {
        if(isset($this->config[$key]))
        {
          return $this->config[$key];
        }
        else
        {
          throw new Exception("Unknown key '$key' in configuration");
        }
      }
      else
      {
        return $this->config;
      }
    }
 
    public function setConfig($config)
    {
      $this->config = $config;
    }
	
	public function getDocUrl(){
		return "<br/><br/>Click <a href='http://flexpaper.devaldi.com/docs_php.jsp'>here</a> for more information on configuring FlexPaper with PHP";
	}
}