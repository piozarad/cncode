%Fanuc-HP4
*Zamienia kod z Fanuc ycm na sterowanie Hp4Mechanicy

*Korektory
/G43
/H%l
/D%l
M6v+D%t

*Bazy
G54/P%l
G54.1 / P%l
G57 => G54
G58 => G55
G59 => G56
G54.1/P%l
G54.1=>G54

*Wszystkie cykle
/G98
/G99

*Cykl G76
G76/Q%l
G76?R=>Z
G76v+G90G0Z400.
G76v+G91G0X0.1
G76v+M19
G76v+M5
G76=>G1

*Cykl G81
G81?R=>R2=
G81?Z=>R3=
G81+R10=400.
G81+R11=3


*Cykl G82
G82?R=>R2=
G82?P=>R4=0.
G82?Z=>R3=
G82+R10=400
G82+R11=3

*Cykl G83
G83?R=>R2=
G83?Z=>R3=
G83+R1=10.
G83+R4=1.
G83?Q=>R5=
G83+R10=400.
G83+R11=3

*Cykl G84
G84?R=>R2=
G84?Z=>R3=
G84+R6=4
G84+R7=3
G84+R10=400.
G84+R11=3


*Kody maszynowe
M12 => M8
KL>M29 
/M41
/M43
M99=>M30


*Obroty Stolu
KL>G30
KL>G28
G65v+M0(TU BYL BLOK Z MAKRO)
G65/P%l
/G65
KL>M130
KL>M128
KL>M127


*Inne
O=>%MPF
M30v+%



