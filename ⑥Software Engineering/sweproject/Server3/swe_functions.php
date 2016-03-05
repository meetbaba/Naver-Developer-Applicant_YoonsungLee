<?php
include 'statics.php';
/*
  This is the class in which the functions which are used for communication server
  are collected.


*/

/*
   name : checkMessageTable
   function : check the table which has the messages for sending other client.
              & return the message for sending.
   parameters
    - $table_name : the table name which is searched for checking message.

   return : $message['tag']['content']
*/
function checkMessageTable($table_name){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

    //connect to DB and table.
    $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
    mysqli_select_db($conn, $DB_NAME);
    $table_result = mysqli_query($conn, "SELECT * FROM ".$table_name);


    //make message
    if($row=mysqli_fetch_assoc($table_result)){

      $message['flag']=$row['flag'];
      for($i=0; $i<$row['num_contents']; $i++){
        $tag="tag_".($i+1);
        $content="content_".($i+1);
        $message[$row[$tag]]=$row[$content];
      }
    }else{

      if($table_name==$MESSAGE_TABLE2){
        $message['flag']=(string)"200";
      }else if($table_name==$MESSAGE_TABLE4){
        $message['flag']=(string)"400";
      }
    }

    //delete message from the table.
    $sql="DELETE FROM ".$table_name." WHERE id=".$row['id'];
    if(mysqli_query($conn, $sql)){
      //query success!
    }else{
      //query failed!
    }
    //return the message.
    return $message;
}

/*
   name : storeTable
   function : store the datas(flag, num_contents, tags, contents) into the table.

   parameters
    - $table_name : the table name in which the datas will be stored.
    - $flag : the flag data.
    - $num_contents : the num_contents data.
                      This is used for check how many contents is in content array.
    - $content['int'] : the data array which will be stored into the database table in the tag & content colums.
              'int' : 0, 2, 4, ... : tag name
                    : 1, 3, 5, ... : content for each tag
              example : $content[0]="vehicle_code"
                        $content[1]="123456"
                        -> message ('flag','num_content','vehicle_code') value(100, 1, 123456)

   return : ( true / false ) store query result
*/
function storeMessageTable($table_name, $flag, $num_contents, $content){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);

  $sql="INSERT INTO ".$table_name." (flag,num_contents";
  $sql_tail="VALUES('".$flag."', ".$num_contents;

  for($i=0; $i<$num_contents; $i++){
    $sql=$sql.",tag_".($i+1).",content_".($i+1);
    $tmp_content1=$content[(2*$i)];
    $tmp_content2=$content[(2*$i)+1];
    $tmp_sql=", '".$tmp_content1."', '".$tmp_content2."'";
    $sql_tail=$sql_tail.$tmp_sql;
  }
  $sql=$sql.") ";
  $sql_tail=$sql_tail.")";

  $sql=$sql.$sql_tail;
  if(mysqli_query($conn, $sql)){
    return true;
  }else{
    return false;
  }

}

/*
   name : checkReservationTable
   function : check the reservation_table to find the corresponding vehicle_code
              because there is no duplicate vehicle_code in the reservation_table.
   parameters
    - $table_name : the table name in which reservation list is stored.
    - vehicle_code : vehicle_code we want to find.
   return : true : same vehicle_code is already in the table.
            false : same vehicle_code do not exist in the table.
*/
function checkReservationTable($vehicle_code){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);
  $table_result = mysqli_query($conn, "SELECT * FROM ".$RESERVATION_TABLE);

  while($row=mysqli_fetch_assoc($table_result)){
    if($row['vehicle_code']==$vehicle_code){
      return true;
    }
  }
  return false;
}

/*
   name : storeReservationTable
   function : store the datas(vehicle_code, user_id, user_mac)in the reservation_table.
   parameters
    - $table_name : the table name in which reservation list is stored.
    - $vehicle_code : vehicle_code we want to store.
    - $user_id
    - $user_mac
   return :( true / false ) store query result.
*/
function storeReservationTable($vehicle_code, $user_id, $user_mac){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);

  $sql="INSERT INTO ".$RESERVATION_TABLE." (vehicle_code,user_id,user_mac) VALUES('".$vehicle_code."', '".$user_id."', '".$user_mac."')";

  if(mysqli_query($conn, $sql)){
    return true;
  }else{
    return false;
  }
}

/*
   name : putoutReservationTable
   function : put out the datas(vehicle_code, user_id, user_mac)from the reservation_table.
   parameters
    - $vehicle_code : vehicle_code we want to store.
    - $user_id
   return : result[]=array('vehicle_code', 'user_id', 'user_mac').
            "false" : there is no matched vehicle_code & user_id.
*/
function putoutReservationTable($vehicle_code, $user_id){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);
  $table_result = mysqli_query($conn, "SELECT * FROM ".$RESERVATION_TABLE);

  $sql="DELETE FROM ".$RESERVATION_TABLE." WHERE vehicle_code=".$vehicle_code;

  while($row=mysqli_fetch_assoc($table_result)){

    if($row['vehicle_code']==$vehicle_code){

      if( $row['user_id']==$user_id){

        $message['vehicle_code']=$row['vehicle_code'];
        $message['user_id']=$row['user_id'];
        $message['user_mac']=$row['user_mac'];

        mysqli_query($conn, $sql);


        return $row['user_mac'];
      }
    }
  }

  return "false";
}

/*
   name : checkRegisteredUserId
   function : check the table whether there is already vehicle code matched with user_id.
    - $vehicle_code : vehicle_code we want to store.
   return : "$user_id" : There is registered user_id. return registed user_id.
            "false" : There is no registed user_id. return string "false".
*/
function checkRegisteredUserId($vehicle_code){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);
  $table_result = mysqli_query($conn, "SELECT * FROM ".$MATCHING_TABLE);

  while($row=mysqli_fetch_assoc($table_result)){
    if($row['vehicle_code']==$vehicle_code){
      if($row['user_id']!=NULL){
        return $row['user_id'];
      }
    }
  }

  return "false";
}

/*
   name : checkExistingVehicleCode
   function : check the table whether there is already vehicle code in the table.
    - $vehicle_code : vehicle_code we want to check.
   return : true : There is vehicle_code.
            false : There is no vehicle_code.
*/
function checkExistingVehicleCode($vehicle_code){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);
  $table_result = mysqli_query($conn, "SELECT * FROM ".$MATCHING_TABLE);

  while($row=mysqli_fetch_assoc($table_result)){
    if($row['vehicle_code']==$vehicle_code){
      return true;
      }
    }
      return false;
  }



/*
   name : checkExistingVehicleCode
   function : check the table whether there is already vehicle code in the table.
    - $vehicle_code : vehicle_code we want to check.
   return : true : There is vehicle_code.
            false : There is no vehicle_code.
*/
function updateVehicleCodeMatching($vehicle_code, $user_id, $user_mac){

  global $HOST, $DB_USER, $DB_PASSWORD, $DB_NAME, $MATCHING_TABLE, $MESSAGE_TABLE2, $MESSAGE_TABLE4, $RESERVATION_TABLE;

  //connect to DB and table.
  $conn=mysqli_connect($HOST, $DB_USER, $DB_PASSWORD);
  mysqli_select_db($conn, $DB_NAME);
  $table_result = mysqli_query($conn, "SELECT * FROM ".$MATCHING_TABLE);

  while($row=mysqli_fetch_assoc($table_result)){
    if($row['vehicle_code']==$vehicle_code){



      return true;
      }
    }

      return false;
  }

 ?>
