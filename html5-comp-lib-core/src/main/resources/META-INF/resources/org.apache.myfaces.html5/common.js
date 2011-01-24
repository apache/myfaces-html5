/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

var myfaces;
if(myfaces == undefined || myfaces == null)
	myfaces = {};

if(myfaces.html5 == undefined || myfaces.html5 == null)
    myfaces.html5 = {};

if(myfaces.html5.common == undefined || myfaces.html5.common == null)
    myfaces.html5.common = {};

/**
 * Converts given array argument to comma separated string.
 * @param arr
 */
myfaces.html5.common.getArrayAsString = function(arr){
   var retVal = "";
   for(var key in arr){
	   if(key){
		   var elem = arr[key];
		   if(elem){
			   if(elem != "")
				   retVal += elem + ",";
		   }
	   }
   }
   if(retVal != "")
	   retVal = retVal.substring(0, retVal.length-1);
   
   return retVal;
}

/**
 * Converts given string argument to array by splitting it with comma character.
 * @param str
 */
myfaces.html5.common.convertStringToArray = function(str){
   return str.split(",");
}

/**
 * Checks whether given array has the given string value.
 * @param arr
 * @param str
 */
myfaces.html5.common.contains = function(arr, str){
    if(! str)
    	return false;
	for(var i=0; i<arr.length; i++){
	   if(arr[i]==str)
		   return true;
   }
   return false;
}

/**
 * Return true if first arg ends with second one.
 * @param str
 * @param suffix
 */
myfaces.html5.common.strEndsWith = function(str, suffix) {
    return str.match(suffix+"$")==suffix;
}