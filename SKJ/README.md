## Każy projekt posiada w swoim folderze dokumentację (jak i komentarze w kodzie), krótki opis:  
  
- **VPN ->** Projekt wielowątkowy realizujący prosty tunel VPN, dzięki czemu dwaj użytkownicy mogą się komunikować między sobą poprzez niezależny serwer. Użytkownik może wybrac dwie klasy do komunikacji z serwerem (jedna działa na gniazdku UDP, a druga na TCP)  
  
- **PortKnocking ->** Projekt komunikacji między serwerem, a klientem. Klient poprzez UDP wysyła wiadomość podzieloną na kilka pakietów do serwera, jeżeli serwer dostanie je wszystkie oraz będą one od tego samego nadawcy to poprzez UDP wysyła klientowi potrzebne dane do połączenia się z nowo otwartym gniazdkiem TCP, na którym oczekuje na połączenie.  
  
- **WielkiTurniej ->** Sieciowa gra w marynarza, po połączeniu się klient rozgrywa automatyczną wyliczankę z każdym graczem, który jest obecnie w lobby. Po decyzji, że nie chcemy już dalej grać wyświetla nasze wszystkie wyniki dotychczasowych gier.
