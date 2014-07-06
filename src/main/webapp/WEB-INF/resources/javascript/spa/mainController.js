
angular
    .module('airvals', ['ngRoute', 'pascalprecht.translate', 'ngMap'])
    .config(['$httpProvider', function($httpProvider) {
        delete $httpProvider.defaults.headers.common["X-Requested-With"]
    }])
    .config(function ($routeProvider) {
        var baseUrl = '/diplomarbeit/resources/spa-views/';
        $routeProvider
            .when('/', {
                templateUrl: baseUrl + 'flights.html'
            })
    })
    .config(function($translateProvider) {
        $translateProvider.translations('en', {
            LOG_IN : 'login',
            SOURCE_PLACEHOLDER : 'what city are you leaving behind?',
            DEST_PLACEHOLDER : 'what city are you traveling to?',
            ONE_WAY : 'one way?',
            DIRECT_FLIGHT : 'direct flight?',
            START_DATE_PLACEHOLDER : 'when are you leaving?',
            RETURN_DATE_PLACEHOLDER : 'when are you returning?',
            DEPARTURE : 'Departure',
            RETURN : 'Return',
            PASSWORD : 'password',
            EMAIL : 'email',
            BOOK_BUY_BUTTON_TEXT : 'book / buy',
            LOGIN_MODAL_HEADER_TEXT : ' Please fill in your username and password combination',

            CREATE_ACCOUNT_TEXT : 'create account',
            ADMIN_LOG_IN_TEXT : 'admin login',

            REGISTRATION_MODAL_HEADER_TEXT : 'Please fill in your data',
            REGISTRATION_MODAL_FIRST_NAME_PLACEHD : 'what is your first name?',
            REGISTRATION_MODAL_LAST_NAME_PLACEHD : 'what is your last name?',
            REGISTRATION_MODAL_CREATE_ACCOUNT_BTN : 'create account',

            ADMIN_LOGIN_MODAL_HEADER : 'Please fill in your username and password',
            ADMIN_LOGIN_FOOTER_BTN : 'depart',

            BUY_TICKET_MODAL_FAMILY_NAME : 'Family name',
            BUY_TICKET_MODAL_SURNAME : 'Surname',
            BUY_TICKET_MODAL_ID_SERIES : 'ID Series',
            BUY_TICKET_MODAL_ID_NUMBER : 'ID Number',
            BUY_TICKET_MODAL_PHONE_NUMBER : 'Phone number',
            BUY_TICKET_MODAL_CONFIRM_PAY : 'Confirm pay',
            BUY_TICKET_MODAL_BOOK : 'Book',

            TICKET_BOUGHT_MODAL_CONGRATULATIONS_TITLE : 'Congratulations!',
            TICKET_BOUGHT_MODAL_CONGRATULATIONS_TEXT : 'You have successfully purchased your ticket!',
            TICKET_BOUGHT_MODAL_BUTTON_TEXT : 'Click to open it',

            PERSONAL_INFO_MODAL_HEADER : 'Here are your past actions:',

            SOURCE_CITY_INVALID : 'The source city is invalid!',
            DESTINATION_CITY_INVALID : 'The destination city is invalid!',
            DEPARTURE_DATE_INVALID : 'Incorrect departure date!',
            RETURNING_DATE_INVALID : 'Incorrect returning date!'
        })
        .translations('de', {

                // ä - \xE4
                // ö - \xF6
                // ü - \xFC

                LOG_IN : 'login',
                SOURCE_PLACEHOLDER : 'welcher Stadt sind Sie hinter sich lassen?',
                DEST_PLACEHOLDER : 'in welcher Stadt m\xF6chten Sie reisen an',
                ONE_WAY : 'Einweg?',
                DIRECT_FLIGHT : 'Direktflug?',
                START_DATE_PLACEHOLDER : 'wenn Sie starten?',
                RETURN_DATE_PLACEHOLDER : 'wann werden Sie zur\xFCck?',
                DEPARTURE : 'Abfahrt',
                RETURN : 'R\xFCckkehr',
                PASSWORD : 'Passwort',
                EMAIL : 'email',
                BOOK_BUY_BUTTON_TEXT : 'kaufen',
                LOGIN_MODAL_HEADER_TEXT : 'Bitte geben Sie Ihre email und Passwort Kombination',

                CREATE_ACCOUNT_TEXT : 'Benutzerkonto erstellen',
                ADMIN_LOG_IN_TEXT : 'admin login',

                REGISTRATION_MODAL_HEADER_TEXT : 'Bitte f\xFCllen Sie Ihre Daten',
                REGISTRATION_MODAL_FIRST_NAME_PLACEHD : 'was ist Ihr Vorname?',
                REGISTRATION_MODAL_LAST_NAME_PLACEHD : 'was ist dein Familienname?',
                REGISTRATION_MODAL_CREATE_ACCOUNT_BTN : 'Benutzerkonto erstellen',

                ADMIN_LOGIN_MODAL_HEADER : 'Bitte geben Sie Ihre email und Passwort Kombination',
                ADMIN_LOGIN_FOOTER_BTN : 'abweichen',

                BUY_TICKET_MODAL_FAMILY_NAME : 'Familienname',
                BUY_TICKET_MODAL_SURNAME : 'Vorname',
                BUY_TICKET_MODAL_ID_SERIES : 'ID-Serie',
                BUY_TICKET_MODAL_ID_NUMBER : 'ID-Nummer',
                BUY_TICKET_MODAL_PHONE_NUMBER : 'Telefonnummer',
                BUY_TICKET_MODAL_CONFIRM_PAY : 'Zahlung bestätigen',
                BUY_TICKET_MODAL_BOOK : 'Buchen',

                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TITLE : 'Herzlichen Gl\xFCckwunsch!',
                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TEXT : 'Sie haben erfolgreich Ihr Ticket gekauft!',
                TICKET_BOUGHT_MODAL_BUTTON_TEXT : 'Klicken Sie, um sie zu \xF6ffnen',

                PERSONAL_INFO_MODAL_HEADER : 'Hier sind Ihre letzten Aktionen:',

                SOURCE_CITY_INVALID : 'Quelle Stadt ist ung\xFCltig',
                DESTINATION_CITY_INVALID : 'Zielort ist ung\xFCltig',
                DEPARTURE_DATE_INVALID : 'Abreisedatum ist ung\xFCltig',
                RETURNING_DATE_INVALID : 'Rückflugtermin ist ung\xFCltig'
        })
        .translations('fr', {
                // è - \xE8
                // à - \xE0
                // î - \xEE
                // é - \xE9

                LOG_IN : 'login',
                SOURCE_PLACEHOLDER : 'quelle ville partez-vous derri\xE8re',
                DEST_PLACEHOLDER : 'quelle ville partez-vous \xE0?',
                ONE_WAY : "une mani\xE8re?",
                DIRECT_FLIGHT : 'vol direct?',
                START_DATE_PLACEHOLDER : 'quand partez-vous?',
                RETURN_DATE_PLACEHOLDER : 'quand vous retournez?',
                DEPARTURE : 'D\xE8part',
                RETURN : 'Retour',
                PASSWORD : 'mot de passe',
                EMAIL : 'email',
                BOOK_BUY_BUTTON_TEXT : 'acheter',
                LOGIN_MODAL_HEADER_TEXT : 'S\'il vous pla\xEEt remplir dans votre e-mail et mot de passe combinaison',

                CREATE_ACCOUNT_TEXT : 'créer un compte',
                ADMIN_LOG_IN_TEXT : 'admin login',

                REGISTRATION_MODAL_HEADER_TEXT : 'S\'il vous plaît remplir vos données',
                REGISTRATION_MODAL_FIRST_NAME_PLACEHD : 'quel est votre prénom?',
                REGISTRATION_MODAL_LAST_NAME_PLACEHD : 'quel est votre nom de famille?',
                REGISTRATION_MODAL_CREATE_ACCOUNT_BTN : 'cr\xE9er un compte',

                ADMIN_LOGIN_MODAL_HEADER : 'S\'il vous plaît remplir dans votre e-mail et mot de passe combinaison',
                ADMIN_LOGIN_FOOTER_BTN : 'd\xE9part',

                BUY_TICKET_MODAL_FAMILY_NAME : 'Nom de famille',
                BUY_TICKET_MODAL_SURNAME : 'Prénom',
                BUY_TICKET_MODAL_ID_SERIES : 'S\xE9rie ID',
                BUY_TICKET_MODAL_ID_NUMBER : 'Num\xE9ro d\'identification',
                BUY_TICKET_MODAL_PHONE_NUMBER : 'Num\xE9ro de t\xE9l\xE9phone',
                BUY_TICKET_MODAL_CONFIRM_PAY : 'confirmer le paiement',
                BUY_TICKET_MODAL_BOOK : 'r\xE9server',

                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TITLE : 'F\xE9licitations!',
                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TEXT : 'Vous avez r\xE9ussi \xE0 acheter votre billet!',
                TICKET_BOUGHT_MODAL_BUTTON_TEXT : 'Cliquez pour ouvrir',

                PERSONAL_INFO_MODAL_HEADER : 'Voici vos actions pass\xE9es:',

                SOURCE_CITY_INVALID : 'La ville source n\'est pas valide',
                DESTINATION_CITY_INVALID : 'La ville de destination n\'est pas valide',
                DEPARTURE_DATE_INVALID : 'Date de d\xE9part n\'est pas valide',
                RETURNING_DATE_INVALID : 'date de retour n\'est pas valide'
        })
        .translations('ro', {
                LOG_IN : 'login',
                SOURCE_PLACEHOLDER : 'de unde plecati?',
                DEST_PLACEHOLDER : 'unde mergeti?',
                ONE_WAY : 'doar dus?',
                DIRECT_FLIGHT : 'zbor direct?',
                START_DATE_PLACEHOLDER : 'cand plecati?',
                RETURN_DATE_PLACEHOLDER : 'cand va intoarceti?',
                DEPARTURE : 'Plecare',
                RETURN : 'Intoarcere',
                PASSWORD : 'Parola',
                EMAIL : 'email',
                BOOK_BUY_BUTTON_TEXT : 'cumparare/rezervare',
                LOGIN_MODAL_HEADER_TEXT : 'Va rugam introduceti utilizatorul si parola',

                CREATE_ACCOUNT_TEXT : 'creaza cont',
                ADMIN_LOG_IN_TEXT : 'admin login',

                REGISTRATION_MODAL_HEADER_TEXT : 'Va rugam introduceti utilizatorul si parola',
                REGISTRATION_MODAL_FIRST_NAME_PLACEHD : 'prenume',
                REGISTRATION_MODAL_LAST_NAME_PLACEHD : 'nume',
                REGISTRATION_MODAL_CREATE_ACCOUNT_BTN : 'creaza cont',

                ADMIN_LOGIN_MODAL_HEADER : 'Va rugam introduceti utilizatorul si parola',
                ADMIN_LOGIN_FOOTER_BTN : 'go!',

                BUY_TICKET_MODAL_FAMILY_NAME : 'Nume',
                BUY_TICKET_MODAL_SURNAME : 'Prenume',
                BUY_TICKET_MODAL_ID_SERIES : 'Seria C.I.',
                BUY_TICKET_MODAL_ID_NUMBER : 'Numar C.I.',
                BUY_TICKET_MODAL_PHONE_NUMBER : 'Nr. telefon',
                BUY_TICKET_MODAL_CONFIRM_PAY : 'Confirma plata',
                BUY_TICKET_MODAL_BOOK : 'Rezerva',

                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TITLE : 'Felicitari!',
                TICKET_BOUGHT_MODAL_CONGRATULATIONS_TEXT : 'Sunteti posesorul unui bilet Airvals!',
                TICKET_BOUGHT_MODAL_BUTTON_TEXT : 'Click pentru a-l deschide',

                PERSONAL_INFO_MODAL_HEADER : 'Istoricul dumneavoastra',

                SOURCE_CITY_INVALID : 'Orasul sursa este invalid!',
                DESTINATION_CITY_INVALID : 'Orasul destinatie este invalid!',
                DEPARTURE_DATE_INVALID : 'Data plecarii este incorecta!',
                RETURNING_DATE_INVALID : 'Data de intoarcere este incorecta!'
        });

        $translateProvider.preferredLanguage('en');
    })
    .controller('mainController', function($scope) {
    });