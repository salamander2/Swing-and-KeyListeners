# Swing-and-KeyListeners

*Various useful ways of doing keylisteners in Swing.*

------------

When you use a KeyListener in Java, you have to decide whether you are using
key codes or key characters. And then you have to 
be consistent with this throughout your program.

**Key characters**
* allow you to say: if (key == 'a') which is easy to understand.
* They also distinguish between 'A' and 'a'.
* But they cannot handle non-alphanumeric keys like CTRL or FN1 or arrow keys.

**Key Codes**
* return a different code for each key that you press.
* You can now use every key on the keyboard and even distinguish between
keypad keys and normal keys.
* It's a bit more complicated to use: you either have to know that the up arrow is 38
or else you have to use a built-in constant (KeyEvent.VK_UP).
* they do not distinguish between 'A' and 'a' since it's the same key being pressed.

### In these examples, I show how to use Key characters in AnimationAndKeys.java and KeyCodes in AnimationAndKeys2.java


## Differing requirements for games
All of the following are illustrated in the code.

1. normal response to a key press:  if you press 'a', move left 5 pixels
2. slow response to a key press. Pressing 'q' or 'e' will go through a list of items one at a time.
2. responding to a keypress without the built in key-delay / key repeat delay, ie. having smooth motion
3. setting an action so that it can only happen once per keypress. For example, holding down the 's' key will only shrink the player once, not infinitely.

:boom: With different parts of the program doing different things with status of objects, you have to really be aware of where and when repainting needs to be done.

-------------------

##Note
According to Java documentation the keyTyped() event doesn't have key code information, 
and keyPressed() and keyReleased() events don't have key character information.

HOWEVER, keyPressed and keyReleased do give you characters from e.getKeyChar()
