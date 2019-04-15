## Spis Treści
+ [Zadanie 1 - Linear classification](#zadanie-1---linear-classification)
+ [Zadanie 2 - Delta algorithm](#zadanie-2---delta-algorithm)
+ [Zadanie 3 - Jednowarstwowa sieć neuronowa rozpoznająca języki](#zadanie-3---Jednowarstwowa-sieć-neuronowa-rozpoznająca-języki)

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


## Zadanie 3 - Jednowarstwowa sieć neuronowa rozpoznająca języki

Dane dzielimy na zbiór treningowy i testowy w proporcji 50-50.
Wczytujemy pliki, usuwamy znaki „narodowe”, zliczamy częstości wystąpień znaków „a”-„z” (zakres od 0.0 do 1.0) i tworzymy dla każdego tekstu wektor częstości.
Następnie algorytmem delty trenujemy perceptrony, które rozróżniają poszczególne języki. Zbudujemy z nich jednowarstwową sieć perceptronową.
Testujemy ją potem na danych ze zbioru testowego. Wypisujemy macierz omyłek i miary jakości dla każdej klasy.
Program musi umożliwiać wielokrotne wprowadzenie tekstu przykładowego i podanie dla takiego tekstu jego wynik klasyfikacji.
