<?php
	session_start();  
    if (!isset($_SESSION['log'])||$_SESSION["log"]!='1') { 
        die ('U bent niet ingelogd!<br />'.'<a href="/index.php">Ga terug naar home</a>');	       
		}
		
?>