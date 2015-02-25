(ns ^:figwheel-always tutorial.notepad.core
    (:require [goog.dom :as dom]
              [goog.ui.Zippy]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

;; Makes a DOM element for a single note.
(defn make-note-dom [note]
  (let [header-element
        (dom/createDom "div"
                       #js {:style "background-color:#EEE"}
                       (.-title note))
        content-element (dom/createDom "div" nil (.-content note))
        new-note-dom (dom/createDom "div" nil
                                    header-element content-element)]
    (dom/appendChild (.-parent note) new-note-dom)
    (goog.ui.Zippy. header-element content-element)))


;;"Main" function for page.
(defn main []
  (let [notes-container (.getElementById js/document "notes")
        note-titles ["Note 1" "Note2"]
        note-contents ["Content of Note 1" "Content of Note 2"]
        notes (map #(clj->js {:title %1 :content %2 :parent %3})
                   note-titles note-contents (cycle [notes-container]))]
    (println (js/Date.))
    (println (map
              ;; (fn [t c p] #js {:title t :content c :parent p})
              #(clj->js {:title %1 :content %2 :parent %3})
                  note-titles note-contents (cycle [notes-container])))
    (doall (map make-note-dom notes))))

;; Invoke the main function.
(main)
