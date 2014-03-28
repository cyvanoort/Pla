<?php
include 'connect.php';

$la = $_POST['la'];
$lo = $_POST['lo'];

$query = mysqli_query($link, 'INSERT INTO Boards (lng, lat) VALUE ("'. $la.'","'. $lo . '")') or die("er ging iets mis 2 <br />". mysqli_error($link));

echo 'succesvol!';
echo '<meta http-equiv="refresh" content="5; URL=http://plab.carstenoort-pt.com/cms/padd.php">';

?>