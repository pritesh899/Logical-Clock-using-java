# Logical-Clock-using-java


This assignment is based on the principles of clock consistency, associated drifts, inter-process com-munication and the end-to-end argument. 

   I have created a simple distributed system, involving a master object (MO) and four process objects (PO). The MO and each PO will contain a logical clock, a concept proposed by Lamport The concept of the logical clocks along with the following technique, which is based on the Berkeley Algorithm, will attempt to resolve the clock consistency in this system:

1. Each event (send or receive or an internal computation) in the system is associated with a time-stamp, based on logical clocks.

2. Each PO, Pi, will have an associated logical clock, LCi. This logical clock is implemented as a simple counter that is incremented between any successive events executed within that PO. Since a logical clock has a monotonically increasing value, it assigns a unique number to every

event. The time stamp of an event is the value of the logical clock for that event. Hence, if an event A occurs before an event B in Pi, then LCi(A) < LCi(B).

3. The MO will also contain a logical clock, that will be incremented periodically and as indicated in the next point.

4. A Pi will randomly decide (with some probability { a parameter that can be varied) if it wants to carry out an internal event or send a message to the MO or receive a message from the MO. If it decides to send a message, then it will attach the value of its logical clock to that message.

5. Once a MO receives such a message from any PO, it will average all current ve values (i.e., logical clock values of four POs and its logical clock value) and will consider it to be the correct logical clock value. It will then calculate an o set (either positive or negative) for each PO's logical clock and send it to each PO. It will also adjust its logical clock to that correct clock value.

6. A PO will advance its logical clock when it receives a message from the MO containing an o set. If Pi receives a message from MO with an o set ti (could be positive or negative) then Pi should adjust its clock such that LCi is equal to LCi + ti + 1.

7. Any Pi, in the system, may exhibit Byzantine or arbitrary failures.
