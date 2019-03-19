## Spis Treści
+ [Zadanie 1 - Linear classification](#zadanie-1---linear-classification)
+ [Zadanie 2 - Delta algorithm](#zadanie-2---delta-algorithm)

Rozszerzenia poleceń znajdują się w osobnych projektach.  


## Zadanie 1 - Linear classification






## Zadanie 2 - Delta algorithm

Program wczytuje dane z plików tekstowych. Zakładamy, ze:  
• Pierwszy argument programu to plik z danymi treningowymi, drugi z danymi testowymi.  
• Trzeci argument programyu to atrybut decyzyjnt, który chcemy rozróżniać od pozostałych.  
• Atrybut decyzyjny znajduje się w ostatniej kolumnie w plikach tekstowych.  
• Wszystkie atrybuty poza decycyjnym są numeryczne.  
Następnie algorytmem delty trenujemy perceptron, który rozróżnia klasę podaną jako trzeci argument pozostałych klas (na danych treningowych).  
Testujemy go potem na danych ze zbioru testowego.  
Jako wynik wypisuje liczbę prawidłowo zaklasyfikowanych przykładów oraz dokładność eksperymentu wyrażoną w procentach.  
Program umożliwia wielokrotne ręczne wpisanie wektora atrybutów i wypisuje dla takiego wektora czy należy do danej klasy.  
