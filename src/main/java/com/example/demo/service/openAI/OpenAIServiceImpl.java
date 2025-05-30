package com.example.demo.service.openAI;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service
public class OpenAIServiceImpl implements OpenAIService {
	
    @Value("${openai.token}")
    private String token;
    
    @Value("${openai.model}")
    private String model;
    
    @Value("${openai.maxTokens}")
    private int maxTokens;
    
    @Value("${openai.temperature}")
    private double temperature;
	
	public String ChatGPT(String minutes, String purpose) {
		
//        ResourceBundle config = ResourceBundle.getBundle("application");
//        String token = config.getString("openai.token");
//        String model = config.getString("openai.model");
//        int maxTokens = Integer.valueOf(config.getString("openai.maxTokens"));
//        Double temperature = Double.valueOf(config.getString("openai.temperature"));
        
        OpenAiService openAiService = new OpenAiService(token);
        
        List<ChatMessage> messages = new ArrayList<>();
        String chat = "検索クエリを複数立てて、段階的に絞り込みつつ、広範囲な情報を総当たりで収集してください\n"
        		+ "\n"
        		+ "目的 → 行動(複数) → 条件（複数） → 場所（複数）のようにステップごとに考えてください。\n"
        		+ "例えば以下のようにマッピングします：\n"
        		+ "\n"
        		+ "「ギターの練習をしたい」\n"
        		+ "　→「楽器を弾く」\n"
        		+ "　　→「防音」「長時間OK」「電源あり」\n"
        		+ "　　　→「音楽スタジオ」「カラオケボックス」「レンタル防音ルーム」\n"
        		+ "　→「音源を録音する」\n"
        		+ "　　→「録音機材あり」「静音性」「ミキサー利用可」\n"
        		+ "　　　→「レコーディングスタジオ」「自宅スタジオ」「貸しスタジオ」\n"
        		+ "　→「音楽仲間とセッションする」\n"
        		+ "　　→「広さ」「複数人OK」「アンプあり」\n"
        		+ "　　　→「音楽スタジオ」「イベントスペース」「ライブハウス（貸出対応）」\n"
        		+ "\n"
        		+ "「読書したい」\n"
        		+ "　→「本を読む」\n"
        		+ "　　→「静か」「落ち着いた雰囲気」「長時間滞在可能」\n"
        		+ "　　　→「カフェ」「図書館」「自習室」\n"
        		+ "　→「読書感想をまとめる」\n"
        		+ "　　→「Wi-Fi」「電源」「集中しやすい」\n"
        		+ "　　　→「コワーキングスペース」「図書館（PC可）」「ノマド向けカフェ」\n"
        		+ "　→「調べ物をしながら読む」\n"
        		+ "　　→「資料あり」「インターネットアクセス」「辞書など利用可」\n"
        		+ "　　　→「大型書店のラウンジ」「図書館の参考資料室」\n"
        		+ "\n"
        		+ "「軽く運動したい」\n"
        		+ "　→「ウォーキング」\n"
        		+ "　　→「屋外」「開放感」「舗装された道」\n"
        		+ "　　　→「公園」「遊歩道」「河川敷」\n"
        		+ "　→「ストレッチ」\n"
        		+ "　　→「室内」「床が柔らかい」「静か」\n"
        		+ "　　　→「自宅」「地域体育館の部屋」「ヨガスタジオ」\n"
        		+ "　→「軽い筋トレやヨガ」\n"
        		+ "　　→「器具あり」「予約不要」「短時間利用OK」\n"
        		+ "　　　→「市営ジム」「フィットネスジム」「健康増進施設」\n"
        		+ "\n"
        		+ "「作業に集中したい」\n"
        		+ "　→「資料を読む」\n"
        		+ "　　→「静か」「照明明るい」「机と椅子」\n"
        		+ "　　　→「図書館」「自習室」\n"
        		+ "　→「PC作業をする」\n"
        		+ "　　→「Wi-Fi」「電源」「集中しやすい」\n"
        		+ "　　　→「コワーキングスペース」「カフェ（ノマド向け）」\n"
        		+ "　→「Web会議や打ち合わせ」\n"
        		+ "　　→「個室」「防音」「会話OK」\n"
        		+ "　　　→「レンタル会議室」「ワークブース」「シェアオフィス」\n"
        		+ "\n"
        		+ "\n"
        		+ "\n"
        		+ "このように、やりたいこと（目的）に最適な場所カテゴリを推測してください。　\n"
        		+ "\n"
        		+ "その後、検索する際に１つの仮説を立てて検索します。\n"
        		+ "１.単語レベルでの拡張\n"
        		+ "例えば、ギターの練習と検索した際にウクレレの練習ができる場所ではギターの練習が出るという仮説です。ギターとウクレレ辞書的にも意味が近いので拡張できると仮定します。\n"
        		+ "\n"
        		+ "その後、\n"
        		+ "1今後Google Places APIで検索する際、先ほど考えた場所カテゴリが出力されるようなキーワードを考えてください。\n"
        		+ "2考えたキーワードの英語を考えてください。\n"
        		+ "3考えたキーワードに当てはまるGoogle Places APIで使用されているtypes属性があればそれをkeyword属性として出力してください\n"
        		+ "4以下のJSON形式で出力してください。\n"
        		+ "\n"
        		+ "\n"
        		+ "{\n"
        		+ "    \"keyword\": [\"水\",\"water\", ...]\n"
        		+ "}\n"
        		+ "\n"
        		+ "\n"
        		+ "注意点\n"
        		+ "１例えばピザの注文と目的を考えた際にキーワードに「デリバリー」となってしまう場合がないようにしてください。この後GooglePlaceAPIでtextsearchをするのでデリバリーが来ると車屋さんなどの目的：ピザの注文とはかけ離れた検索結果が出てしまいます。このことを考えてキーワードを出力してください\n"
        		+ "２例えば目的：ピザの注文ではピザ屋だけでなくイタリアンレストランでも注文できる可能性があります。そのようなことを幅広く考えてキーワードを出力してください\n"
        		+ "３JSONのみ返してください.。文章や説明は不要です。\n"
        		+ "４マークダウンのコードブロックは無しで書いてください。\n"
        		+ "５出力してくださった文字列をそのままString型からJSON型に変換するのでエラーにならないようにJSON型を出力してください\n"
        		+ "６例えばトイレに行きたいときよくコンビニが使用されることがあります。その目的によく使用される際はキーワードとして挙げてください\n"
        		+ "７これまで上げたキーワードをもう一度見返してみてそのキーワードの具体的な名前をたくさん追加してください。"
        		+ "（例：寿司屋→スシロー、くら寿司…）\n"
        		+ "８その後もう一度キーワードを見返してすべてのキーワードの英語を追加してください\n"
        		+ "\n"
        		+ "目的：" + purpose;
        
        messages.add(new ChatMessage("system", "あなたは、ユーザーの「やりたいこと（目的）」から、それを実現できる「場所のカテゴリ」を推定するAIアシスタントです。")); // システムの役割
        messages.add(new ChatMessage("user", chat));    
        
        ChatCompletionRequest chatRequest = ChatCompletionRequest.builder()
        .model(model)
        .messages(messages)       
        .maxTokens(maxTokens)
        .temperature(temperature)
        .build();

        ChatCompletionResult result = openAiService.createChatCompletion(chatRequest); 
        String reply = result.getChoices().get(0).getMessage().getContent();
        
        return reply;
	}

}
