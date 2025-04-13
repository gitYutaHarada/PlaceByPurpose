package com.example.demo.service.openAI;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Service
public class OpenAIServiceImpl implements OpenAIService {
	
	public String ChatGPT(String range, String purpose) {
		
        ResourceBundle config = ResourceBundle.getBundle("application");
        String token = config.getString("openai.token");
        String model = config.getString("openai.model");
        int maxTokens = Integer.valueOf(config.getString("openai.maxTokens"));
        Double temperature = Double.valueOf(config.getString("openai.temperature"));
        OpenAiService openAiService = new OpenAiService(token);
        
        List<ChatMessage> messages = new ArrayList<>();
        String chat = "淵野辺駅から徒歩" + range + "分以内での場所を探しています。\n"
        		+ "徒歩" + range + "分というのはGooglemapの実測値をベースにしてください\n"
        		+ "\n"
        		+ "また、検索クエリを複数立てて、段階的に絞り込みつつ、広範囲な情報を総当たりで収集してください\n"
        		+ "\n"
        		+ "目的 → 行動 → 条件 → 場所（複数）のようにステップごとに考えるとよい考えにたどり着りつくと思います。\n"
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
        		+ "その後、\n"
        		+ "1今後Places APIで検索する際、先ほど考えた場所カテゴリが出力されるようなキーワードを考えてください\n"
        		+ "2以下のJSON形式で出力してください。\n"
        		+ "\n"
        		+ "\n"
        		+ "{\n"
        		+ "    \"keyword\": [\"水\",\"water\", ...]\n"
        		+ "}\n"
        		+ "\n"
        		+ "JSONのみ返してください\n"
        		+ "出力してくださった文字列をそのままString型からJSON型に変換するのでエラーにならないようにJSON型を出力してください\n"
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
