<?php
include 'connect.php';
$intr = $_POST['intresse'];
$query = mysqli_query($link, 'INSERT INTO Intresses (categorie) VALUE ("'. $intr.'")') or die("er ging iets mis 2 <br />". mysqli_error($link));
echo "succesvol";
echo '<meta http-equiv="refresh" content="5; URL=http://plab.carstenoort-pt.com/cms/iadd.php">';
?>