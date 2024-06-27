/*
 * This source file was generated by the Gradle 'init' task
 */
package bitcamp.project1;

import bitcamp.project1.command.CategoryCommand;
import bitcamp.project1.command.ExpenseCommand;
import bitcamp.project1.command.IncomeCommand;
import bitcamp.project1.util.Prompt;
import org.checkerframework.checker.units.qual.C;

public class App {
    ExpenseCommand expenseCommand = new ExpenseCommand();
    CategoryCommand categoryCommand = new CategoryCommand();
    IncomeCommand incomeCommand = new IncomeCommand();
    String[] mainMenus = new String[]{"수입", "지출", "카테고리", "조회", "종료"};
    String[][] subMenus = {
        {"등록", "조회", "변경", "삭제"},
        {"등록", "조회", "변경", "삭제"},
        {"등록", "목록", "조회", "변경", "삭제"},
        {"이번달 조회", "월별 조회", "연도별 조회", "카테고리별 조회"}
    };

    public static void main(String[] args) {
        new App().execute();
    }

    void execute() {
        printMenu();

        String command;
        while (true) {
            try {
                command = Prompt.input("메인>");

                if (command.equals("menu")) {
                    printMenu();

                } else {
                    int menuNo = Integer.parseInt(command);
                    String menuTitle = getMenuTitle(menuNo, mainMenus); // 설명하는 변수
                    if (menuTitle == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다.");
                    } else if (menuTitle.equals("종료")) {
                        break;
                    } else {
                        processMenu(menuTitle, subMenus[menuNo - 1]);
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    void printMenu() {
        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[가계부]";
        String line = "----------------------------------";

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + appTitle + resetAnsi);

        for (int i = 0; i < mainMenus.length; i++) {
            if (mainMenus[i].equals("종료")) {
                System.out.printf("%s%d. %s%s\n", (boldAnsi + redAnsi), (i + 1), mainMenus[i], resetAnsi);
            } else {
                System.out.printf("%d. %s\n", (i + 1), mainMenus[i]);
            }
        }

        System.out.println(boldAnsi + line + resetAnsi);
    }

    void printSubMenu(String menuTitle, String[] menus) {
        System.out.printf("[%s]\n", menuTitle);
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), menus[i]);
        }
        System.out.println("9. 이전");
    }

    boolean isValidateMenu(int menuNo, String[] menus) {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    String getMenuTitle(int menuNo, String[] menus) {
        return isValidateMenu(menuNo, menus) ? menus[menuNo - 1] : null;
    }

    void processMenu(String menuTitle, String[] menus) {
        printSubMenu(menuTitle, menus);
        while (true) {
            String command = Prompt.input(String.format("메인/%s>", menuTitle));
            if (command.equals("menu")) {
                printSubMenu(menuTitle, menus);
                continue;
            } else if (command.equals("9")) { // 이전 메뉴 선택
                break;
            }

            try {
                int menuNo = Integer.parseInt(command);
                String subMenuTitle = getMenuTitle(menuNo, menus);
                if (subMenuTitle == null) {
                    System.out.println("유효한 메뉴 번호가 아닙니다.");
                } else {
                    switch (menuTitle) {
                        case "수입":
                            incomeCommand.executeIncomeCommand(subMenuTitle);
                            break;
                        case "지출":
                            expenseCommand.executeExpenseCommand(subMenuTitle);
                            break;
                        case "조회":
                            System.out.println("조회입니다.");
                            break;
                        case "카테고리":
                            categoryCommand.executeCategoryCommand(subMenuTitle);
                            break;
                        default:
                            System.out.printf("%s 메뉴의 명령을 처리할 수 없습니다.\n", menuTitle);
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }
    }
}
