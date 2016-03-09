private void mixFiles(){
                try {
                        InputStream is1 = getResources().openRawResource(R.raw.test1);
                        List<Short> music1 = createMusicArray(is1);
                        
                        InputStream is2 = getResources().openRawResource(R.raw.test2);
                        List<Short> music2 = createMusicArray(is2);
                        
                        InputStream is3 = getResources().openRawResource(R.raw.test3);
                        List<Short> music3 = createMusicArray(is3);
                        
                        completeStreams(music1,music2,music3);
                        short[] music1Array = buildShortArray(music1);
                        short[] music2Array = buildShortArray(music2);
                        short[] music3Array = buildShortArray(music3);
                        
                        short[] output = new short[music1Array.length];
                        for(int i=0; i < output.length; i++){
                                
                                float samplef1 = music1Array[i] / 32768.0f;
                                float samplef2 = music2Array[i] / 32768.0f;
                                float samplef3 = music3Array[i] / 32768.0f;
                                
                                float mixed = samplef1 + samplef2 + samplef3;
                                // reduce the volume a bit:
                                mixed *= 0.8;
                                // hard clipping
                                if (mixed > 1.0f) mixed = 1.0f;
                                if (mixed < -1.0f) mixed = -1.0f;
                                short outputSample = (short)(mixed * 32768.0f);
                                
                                
                                output[i] = outputSample;
                        }
                        saveToFile(output);
                } catch (NotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
        }
        