# Java

## First part

see the code :)

## Second part

### Nouveau cycle de release

Des versions rapide tous les 6 mois et une version _Long Time Support_ tous les 3 ans.

version|sortie|end support
*Java 8*|mars 2014|décembre 2020
Java 9|septembre 2017|6 mois
Java 10|mars 2018|6 mois
*Java 11*|septembre 2018|septembre 2022
Java 12|mars 2019|6 mois
Java 13|septembre 2019|6 mois

Prochaine LTS, Java 14 en septembre 2021.

Je vous déconseille d'utiliser les versions non-LTS:

- elles disparaissent à la sortie de la version suivante (moins d'une semaine après la sortie de Java 13, Java 12 n'est plus accessible)
- elles proposent des fonctionnalités *preview* qui peuvent évoluer en cassant la compatibilité d'une version à l'autre (voir les switch de Java 12 et Java 13)
- peu de distributions packagent les versions non-LTS 

### Distributions

Upstream: OpenJDK

L'upstream fait tout le développempent alors que les distributions font le travail de packaging et le support autour de Java.

- [JDK Oracle](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [AdoptOpenJDK](https://adoptopenjdk.net/)
- RedHat au sein de leur distribution
- [Amazon Corretto](https://aws.amazon.com/fr/corretto/)
- [Azul](https://www.azul.com/downloads/zulu-community/)

### What's new in JDK13 ?

- amélioration du code des sockets
- amélioration de ZGC
- amélioration du _Class Data Sharing_ (améliore le temps de démarrage)

En préversion:

- les bloques de texte
- les switch expression

```java
class JusticeLeague {
    String toto = """
    {
        "name": "Bruce Wayne",
        "": "batman"
    }
""";
    enum Member {
        batman, superman, aquaman
    }

    String from(Member nickname) {
        return switch(nickname) {
            case barman -> "Bruce Wayne";
            case superman -> "Clark Kent";
            case aquaman -> "Arthur Curry";
        };
    }
}
```

### Future

#### Loom

Projet visant à ajouter du parallélisme en java.

- green thread
- continuation
- end-tail

https://wiki.openjdk.java.net/display/loom/Main

#### Valhalla

Projet pattern matching/value object…

https://wiki.openjdk.java.net/display/valhalla/Main

#### Sumatra

Utiliser des GPU de manière automatisé
https://openjdk.java.net/projects/sumatra/

### Graalvm

Projet open source développé initialement par Oracle pour palier à certaines limites de HotSpot.

https://www.graalvm.org/

4 parties distincts:

- *Graal Compiler* un compilateur java (remplace peut remplacer le compilateur C2 de hotspot)
- *GraalVM Native Image* java compilé en natif (ahead-of-time)
- *GraalVM SDK* implémentation d'autres langages
- *LLVM Runtime and JavaScript Runtime*
