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

if(myfaces.html5.slide == undefined || myfaces.html5.slide == null){
    myfaces.html5.slide = {};
    myfaces.html5.slide.Slide = function(_element){
        this.element = _element;
    };

    myfaces.html5.slide.Slide.prototype = {
        element : null
    };

    myfaces.html5.slide.SlideView = function(_root, _navigateOnArrowKeys, _navigateOnMouseWheel){
        this.slides = new Array();
        this.slideIndexes = new Array();

        for(var i=0; i<_root.children.length; i++)
        {
            var obj = _root.children[i];
            if(obj.tagName == "DIV"){
                this.slides.push(new myfaces.html5.slide.Slide(obj));
                if(obj.id){
                    this.slideIndexes[obj.id] = this.slideCount;
                }
                this.slideCount++;
            }
        }

        this.setClasses();
        myfaces.html5.effect.addEffect(_root, 'mf-slideview');

        var slideView = this;
        if(_navigateOnArrowKeys){
            document.addEventListener('keydown', function(e) { slideView.handleKeyDown(e); }, false);
        }
        if(_navigateOnMouseWheel){
            document.addEventListener('mousewheel',  function(e) { slideView.handleMouseWheel(e); }, false);
            document.addEventListener('DOMMouseScroll',  function(e) { slideView.handleMouseWheel(e); }, false);
        }


        var userAgent = navigator.userAgent;
        var opera = parseFloat(userAgent.split('Opera/')[1]) || undefined;
        if(opera)
            this.addTransition();

    };

    myfaces.html5.slide.SlideView.prototype = {
        slides : [],
        //slideIndexes : [],
        currentIndex : 0,
        slideCount : 0,

        setClasses : function(){
            var index = 0;
            var slideView = this;
            this.slides.forEach(function(obj)
            {
                if(index < slideView.currentIndex-1)
                    obj.element.className = 'mf-slide mf-slide-hidden-left';
                else if(index == slideView.currentIndex-1)
                    obj.element.className = 'mf-slide mf-slide-previous';
                else if(index == slideView.currentIndex)
                    obj.element.className = 'mf-slide mf-slide-active';
                else if(index == slideView.currentIndex+1)
                    obj.element.className = 'mf-slide mf-slide-next';
                else if(index > slideView.currentIndex+1)
                    obj.element.className = 'mf-slide mf-slide-hidden-right';

                index++;
            });
        },

        addTransition : function(){
            this.slides.forEach(function(obj)
            {
                myfaces.html5.effect.addEffect(obj.element, 'mf-slide-transitioned');
            });
        },

        removeTransition : function(){
            this.slides.forEach(function(obj)
            {
                myfaces.html5.effect.removeEffect(obj.element, 'mf-slide-transitioned');
            });
        },

        left : function(){
            this.currentIndex--;
            if(this.currentIndex<0)
                this.currentIndex = 0;
            this.setClasses();
            this.addTransition();
        },

        right : function(){
            this.currentIndex++;
            if(this.currentIndex > this.slideCount-1)
                this.currentIndex = this.slideCount-1;
            this.setClasses();
            this.addTransition();
        },

        goto : function(newIndex){
            this.removeTransition();
            if(newIndex < 0)
                newIndex = 0;
            if(newIndex > this.slideCount-1)
                newIndex = this.slideCount -1;

            this.currentIndex = newIndex;
            this.setClasses();
            this.addTransition();
        },

        goBySlideId : function(slideId){
            if(this.slideIndexes[slideId]!=undefined && this.slideIndexes[slideId]!=null){
                this.goto(this.slideIndexes[slideId]);
            }
        },

        handleKeyDown: function(e) {
            if(e.target.nodeName == 'INPUT' || e.target.nodeName == 'TEXTAREA')
                return;

            switch (e.keyCode) {
                case 37: // left
                    this.left();
                    break;
                case 32: // space
                case 39: // right
                    this.right();
                    break;
            }
        },

        handleMouseWheel: function(e) {
            var delta = 0;
            if (e.wheelDelta) {
                delta = e.wheelDelta/120;
            }
            else if (e.detail) {
                delta = -e.detail/3;
            }

            if (delta > 0 ) {
                this.left();
                return;
            }
            if (delta < 0 ) {
                this.right();
            }
        }

    };
}