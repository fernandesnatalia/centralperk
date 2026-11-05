package br.com.utils;

public final class Constants {

    // Validation
    public static final String INVALID_ITEM_NAME = "O nome do item não pode ser vazio.";
    public static final String INVALID_ITEM_PRICE = "Preço do item inválido.";
    public static final String INVALID_CUSTOMER_NAME = "Insira seu nome para registrar o pedido.";
    public static final String INVALID_ORDER_NUMBER = "Número do pedido inválido.";
    public static final String INVALID_VALUE = "Valor inválido. Informe um valor igual ou superior a zero.";
    public static final String INVALID_NULL_VALUE = "Não pode ser nulo.";

    // Formatting
    public static final String DIVIDER = "\n===========================================\n";
    public static final String DAILY_ORDERS = "============= PEDIDOS DO DIA ==============";
    public static final String ORDER_REGISTERED = "============ PEDIDO REGISTRADO ============";
    public static final String DAILY_CLOSING = "============ FECHAMENTO DO DIA ============";
    public static final String FOOD_MENU = "================= CARDÁPIO ================";
    public static final String MAIN_MENU = "============== CENTRAL PERK ===============\n";

    // General feedback
    public static final String INVALID_OPTION = "Opção inválida.\n";
    public static final String ORDER_NOT_FOUND = "Pedido não encontrado.";
    public static final String ORDER_CANCELED = "Pedido cancelado.";
    public static final String NO_ORDERS = "\nNenhum pedido registrado.";
    public static final String EMPTY_ORDER = "O pedido não possui itens.";
    public static final String ORDER_ITEMS = "Itens do pedido:";
    public static final String RESTAURANT_CLOSED = "Central Perk encerrado.";
    public static final String CANCELED_STATUS = "Status: CANCELADO";

    private Constants() {
    }
}